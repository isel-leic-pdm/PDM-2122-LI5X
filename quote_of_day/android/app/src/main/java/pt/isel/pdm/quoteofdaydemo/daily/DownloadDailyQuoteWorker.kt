package pt.isel.pdm.quoteofdaydemo.daily

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import pt.isel.pdm.quoteofdaydemo.common.APP_TAG
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayApplication
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayRepository

/**
 * Definition of the background job that fetches the daily quote and stores it in the history DB.
 */
class DownloadDailyQuoteWorker(appContext: Context, workerParams: WorkerParameters)
    : ListenableWorker(appContext, workerParams) {

    override fun startWork(): ListenableFuture<Result> {
        val app : QuoteOfDayApplication = applicationContext as QuoteOfDayApplication
        val repo = QuoteOfDayRepository(app.quoteOfDayService, app.historyDB.getHistoryQuoteDao())

        Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: Starting DownloadDailyQuoteWorker")

        return CallbackToFutureAdapter.getFuture { completer ->
            repo.fetchQuoteOfDay(mustSaveToDB = true) { result ->
                result
                    .onSuccess {
                        Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: DownloadDailyQuoteWorker succeeded")
                        completer.set(Result.success())
                    }
                    .onFailure {
                        Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: DownloadDailyQuoteWorker failed")
                        completer.setException(it)
                    }
            }
        }
    }
}