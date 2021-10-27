package pt.isel.pdm.quoteofdaydemo

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel : ViewModel() {

    init {
        Log.v("APP_TAG", "MainActivityViewModel.init()")

    }

    companion object {
        val service = Retrofit.Builder()
            .baseUrl("https://dee7-194-210-190-253.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteOfDayService::class.java)
    }

    fun getQuoteOfDay(completion: (String) -> Unit) {
        service.getQuote().enqueue(object: Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                completion(response.body()?.text ?: "")
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                Log.e("APP_TAG", "onFailure", t)
            }
        })
    }
}