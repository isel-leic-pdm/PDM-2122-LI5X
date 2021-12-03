package pt.isel.pdm.quoteofdaydemo.common

import android.app.Application
import android.util.Log
import androidx.room.Room
import pt.isel.pdm.quoteofdaydemo.history.HistoryDatabase
import pt.isel.pdm.quoteofdaydemo.history.QuoteEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            .baseUrl("https://bc57-2001-818-e22f-ee00-a505-dff2-e67-79a9.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteOfDayService::class.java)
    }

    /**
     * The database that contains the "quotes of day" fetched so far. ALL of them =)
     */
    val historyDB: HistoryDatabase by lazy {
        Room
            .inMemoryDatabaseBuilder(this, HistoryDatabase::class.java)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        callbackAfterAsync({ }) {
            Log.v(APP_TAG, "Initializing DB")
            historyDB.getHistoryQuoteDao().insert(
                QuoteEntity(
                    id = "2021-11-16", author = "Author 1", content = "A significant quote")
            )
            historyDB.getHistoryQuoteDao().insert(
                QuoteEntity(
                    id = "2021-11-17", author = "Author 2", content = "Another significant quote")
            )
            historyDB.getHistoryQuoteDao().insert(
                QuoteEntity(
                    id = "2021-11-18", author = "Author 3", content = "The most significant quote")
            )
        }
    }
}