package pt.isel.pdm.quoteofdaydemo.common

import android.app.Application
import android.util.Log
import androidx.room.Room
import pt.isel.pdm.quoteofdaydemo.history.HistoryDatabase
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
            .baseUrl("https://448f-2001-818-e22f-ee00-50a1-358-32d0-3dd0.ngrok.io")
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
}