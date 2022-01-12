package pt.isel.pdm.quoteofdaydemo.common

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.isel.pdm.quoteofdaydemo.history.HistoryQuoteDao
import pt.isel.pdm.quoteofdaydemo.history.QuoteEntity

/**
 * Extension function of [QuoteEntity] to conveniently convert it to a [QuoteOfDayDTO] instance.
 * Only relevant for this activity.
 */
fun QuoteEntity.toQuoteOfDayDTO() = QuoteOfDayDTO(
    quote = QuoteDTO(author = this.author, text = this.content),
    date = this.id
)
/**
 * Repository for the Quote Of Day
 * It's role is the one described here: https://developer.android.com/jetpack/guide
 *
 * The repository operations include I/O (network and/or DB accesses) and are therefore asynchronous.
 * For now, and for teaching purposes, we use a callback style to define those operations. Later on
 * we will use Kotlin's way: suspending functions.
 */
class QuoteOfDayRepository(
    private val quoteOfDayService: QuoteOfDayService,
    private val historyQuoteDao: HistoryQuoteDao
) {

    /**
     * Gets the daily quote from the local DB, if available.
     */
    private suspend fun maybeGetTodayQuoteFromDB(): QuoteEntity? {
        Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: maybeGetTodayQuoteFromDB() ")
        return historyQuoteDao.getLast(1).firstOrNull()
    }

    /**
     * Gets the daily quote from the remote API.
     */
    private suspend fun getTodayQuoteFromAPI(): QuoteOfDayDTO =
        withContext(Dispatchers.IO) {
            Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: getTodayQuoteFromAPI() ")
            val response = quoteOfDayService.getQuote().execute()
            val dailyQuote = response.body()
            if (dailyQuote != null && response.isSuccessful) dailyQuote
            else throw ServiceUnavailable()
        }

    /**
     * Saves the daily quote to the local DB.
     */
    private suspend fun saveToDB(dto: QuoteOfDayDTO) =
        historyQuoteDao.insert(
            QuoteEntity(
                id = dto.date,
                author = dto.quote.author,
                content = dto.quote.text
            )
        )

    /**
     * Gets the quote of day, either from the local DB, if available, or from
     * the remote API.
     *
     * @param mustSaveToDB  indicates if the operation is only considered successful if all its
     * steps, including saving to the local DB, succeed. If false, the operation is considered
     * successful regardless of the success of saving the quote in the local DB (the last step).
     * Using a boolean to distinguish between both options is a questionable design decision.
     */
    suspend fun fetchQuoteOfDay(mustSaveToDB: Boolean = false): QuoteOfDayDTO {
        val maybeQuote = maybeGetTodayQuoteFromDB()
        return if (maybeQuote != null) maybeQuote.toQuoteOfDayDTO()
        else {
            val todayQuote = getTodayQuoteFromAPI()
            try { saveToDB(todayQuote); todayQuote }
            catch (e: Exception) {
                if (mustSaveToDB) throw e
                else todayQuote
            }
        }
    }
}
