package pt.isel.pdm.quoteofdaydemo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    init {
        Log.v("APP_TAG", "MainActivityViewModel.init()")
    }

    private val _quoteOfDay: MutableLiveData<Quote> = MutableLiveData()
    val quoteOfDay: LiveData<Quote> = _quoteOfDay

    fun getQuoteOfDay() {
        getApplication<QuoteOfDayApplication>().quoteOfDayService.getQuote().enqueue(
            object: Callback<Quote> {
                override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                    _quoteOfDay.value = response.body()
                }

                override fun onFailure(call: Call<Quote>, t: Throwable) {
                    Log.e("APP_TAG", "onFailure", t)
                }
        })
    }
}