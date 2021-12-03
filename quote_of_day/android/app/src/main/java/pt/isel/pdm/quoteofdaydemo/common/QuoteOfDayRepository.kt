package pt.isel.pdm.quoteofdaydemo.common

import pt.isel.pdm.quoteofdaydemo.history.HistoryQuoteDao
import pt.isel.pdm.quoteofdaydemo.history.QuoteEntity

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
        // TODO
    }

    /**
     * Asynchronously gets the daily quote from the remote API.
     * @param callback the function to be called to signal the completion of the
     * asynchronous operation, which is called in the MAIN THREAD.
     */
    private fun asyncGetTodayQuoteFromAPI(callback: (Result<QuoteOfDayDTO>) -> Unit) {
        // TODO
    }

    /**
     * Asynchronously saves the daily quote to the local DB.
     * @param callback the function to be called to signal the completion of the
     * asynchronous operation, which is called in the MAIN THREAD.
     */
    private fun asyncSaveToDB(dto: QuoteOfDayDTO, callback: (Result<Unit>) -> Unit = { }) {
        // TODO
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
        // TODO:
    }
}
