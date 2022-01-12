package pt.isel.pdm.quoteofdaydemo.daily

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import pt.isel.pdm.quoteofdaydemo.common.APP_TAG
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayApplication
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayRepository

/**
 * Definition of the background job that fetches the daily quote and stores it in the history DB.
 */
class DownloadDailyQuoteWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val app : QuoteOfDayApplication = applicationContext as QuoteOfDayApplication
        val repo = QuoteOfDayRepository(app.quoteOfDayService, app.historyDB.getHistoryQuoteDao())

        Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: Starting DownloadDailyQuoteWorker")
        repo.fetchQuoteOfDay(mustSaveToDB = true)
        return Result.success()
    }
}