package pt.isel.pdm.quoteofdaydemo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val MAIN_ACTIVITY_VIEW_STATE = "MainActivity.ViewState"

class MainActivityViewModel(
    application: Application,
    private val state: SavedStateHandle
) : AndroidViewModel(application) {

    init {
        Log.v("APP_TAG", "MainActivityViewModel.init()")
    }

    val quoteOfDay: LiveData<Quote> = state.getLiveData(MAIN_ACTIVITY_VIEW_STATE)

    fun getQuoteOfDay() {
        getApplication<QuoteOfDayApplication>().quoteOfDayService.getQuote().enqueue(
            object: Callback<Quote> {
                override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                    val quote = response.body()
                    if (quote != null && response.isSuccessful)
                        state.set(MAIN_ACTIVITY_VIEW_STATE, quote)
                }

                override fun onFailure(call: Call<Quote>, t: Throwable) {
                    Log.e("APP_TAG", "onFailure", t)
                }
        })
    }
}