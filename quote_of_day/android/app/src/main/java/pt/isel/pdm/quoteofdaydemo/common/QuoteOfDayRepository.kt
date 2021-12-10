package pt.isel.pdm.quoteofdaydemo.common

import android.util.Log
import pt.isel.pdm.quoteofdaydemo.history.HistoryQuoteDao
import pt.isel.pdm.quoteofdaydemo.history.QuoteEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
     * Asynchronously gets the daily quote from the local DB, if available.
     * @param callback the function to be called to signal the completion of the
     * asynchronous operation, which is called in the MAIN THREAD.
     */
    private fun asyncMaybeGetTodayQuoteFromDB(callback: (Result<QuoteEntity?>) -> Unit) {
        callbackAfterAsync(callback) {
            historyQuoteDao.getLast(1).firstOrNull()
        }
    }

    /**
     * Asynchronously gets the daily quote from the remote API.
     * @param callback the function to be called to signal the completion of the
     * asynchronous operation, which is called in the MAIN THREAD.
     */
    private fun asyncGetTodayQuoteFromAPI(callback: (Result<QuoteOfDayDTO>) -> Unit) {
        quoteOfDayService.getQuote().enqueue(
            object: Callback<QuoteOfDayDTO> {
                override fun onResponse(call: Call<QuoteOfDayDTO>, response: Response<QuoteOfDayDTO>) {
                    Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: onResponse ")
                    val dailyQuote: QuoteOfDayDTO? = response.body()
                    val result =
                        if (dailyQuote != null && response.isSuccessful)
                            Result.success(dailyQuote)
                        else
                            Result.failure(ServiceUnavailable())
                    callback(result)
                }

                override fun onFailure(call: Call<QuoteOfDayDTO>, error: Throwable) {
                    Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: onFailure ")
                    callback(Result.failure(ServiceUnavailable(cause = error)))
                }
            })
    }

    /**
     * Asynchronously saves the daily quote to the local DB.
     * @param callback the function to be called to signal the completion of the
     * asynchronous operation, which is called in the MAIN THREAD.
     */
    private fun asyncSaveToDB(dto: QuoteOfDayDTO, callback: (Result<Unit>) -> Unit = { }) {
        callbackAfterAsync(callback) {
            historyQuoteDao.insert(
                QuoteEntity(
                    id = dto.date,
                    author = dto.quote.author,
                    content = dto.quote.text
                )
            )
        }
    }

    /**
     * Asynchronously gets the quote of day, either from the local DB, if available, or from
     * the remote API.
     *
     * @param mustSaveToDB  indicates if the operation is only considered successful if all its
     * steps, including saving to the local DB, succeed. If false, the operation is considered
     * successful regardless of the success of saving the quote in the local DB (the last step).
     * @param callback the function to be called to signal the completion of the
     * asynchronous operation, which is called in the MAIN THREAD
     *
     * Using a boolean to distinguish between both options is a questionable design decision.
     */
    fun fetchQuoteOfDay(mustSaveToDB: Boolean = false, callback: (Result<QuoteOfDayDTO>) -> Unit) {
        asyncMaybeGetTodayQuoteFromDB { maybeEntity ->
            val maybeQuote = maybeEntity.getOrNull()
            if (maybeQuote != null) {
                Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: Got daily quote from local DB")
                callback(Result.success(maybeQuote.toQuoteOfDayDTO()))
            }
            else {
                asyncGetTodayQuoteFromAPI { apiResult ->
                    apiResult.onSuccess { quoteDto ->
                        Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: Got daily quote from API")
                        asyncSaveToDB(quoteDto) { saveToDBResult ->
                            saveToDBResult.onSuccess {
                                Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: Saved daily quote to local DB")
                                callback(Result.success(quoteDto))
                            }
                                .onFailure {
                                    Log.e(APP_TAG, "Thread ${Thread.currentThread().name}: Failed to save daily quote to local DB", it)
                                    callback(if(mustSaveToDB) Result.failure(it) else Result.success(quoteDto))
                                }
                        }
                    }
                    callback(apiResult)
                }
            }
        }
    }
}
