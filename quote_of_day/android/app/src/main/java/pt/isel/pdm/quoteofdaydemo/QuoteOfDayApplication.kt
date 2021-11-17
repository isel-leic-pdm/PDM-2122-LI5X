package pt.isel.pdm.quoteofdaydemo

import android.app.Application
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val APP_TAG = "QuoteOfDay"

class QuoteOfDayApplication : Application() {

    init {
        Log.v(APP_TAG, "QuoteOfDayApplication.init for ${hashCode()}")
    }

    val quoteOfDayService by lazy {
        Retrofit.Builder()
            .baseUrl("https://6132-194-210-190-96.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteOfDayService::class.java)
    }
}