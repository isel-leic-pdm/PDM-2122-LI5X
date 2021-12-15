package pt.isel.pdm.quoteofdaydemo.common

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.work.*
import pt.isel.pdm.quoteofdaydemo.daily.DownloadDailyQuoteWorker
import pt.isel.pdm.quoteofdaydemo.history.HistoryDatabase
import pt.isel.pdm.quoteofdaydemo.history.QuoteEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val APP_TAG = "QuoteOfDay"

/**
 * We use the application object for resolving dependencies with global significance.
 */
class QuoteOfDayApplication : Application() {

    init {
        Log.v(APP_TAG, "QuoteOfDayApplication.init for ${hashCode()}")
    }

    /**
     * The service used to reach the remote API that provides the quotes
     */
    val quoteOfDayService: QuoteOfDayService by lazy {
        Retrofit.Builder()
            .baseUrl("https://0ecc-193-236-165-2.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteOfDayService::class.java)
    }

    /**
     * The database that contains the "quotes of day" fetched so far. ALL of them =)
     */
    val historyDB: HistoryDatabase by lazy {
        Room
            .databaseBuilder(this, HistoryDatabase::class.java, "history_db")
            .build()
    }

    /**
     * Called each time the application process is loaded
     */
    override fun onCreate() {
        super.onCreate()
        val workRequest = PeriodicWorkRequestBuilder<DownloadDailyQuoteWorker>(1, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "DownloadDailyQuote",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}