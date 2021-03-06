package pt.isel.pdm.quoteofdaydemo.daily

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pt.isel.pdm.quoteofdaydemo.common.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val VIEW_STATE = "MainActivity.ViewState"

/**
 * The actual execution host behind the application's Main screen (i.e. the [MainActivity]).
 */
class MainActivityViewModel(
    application: Application,
    private val savedState: SavedStateHandle
) : AndroidViewModel(application) {

    init {
        Log.v("APP_TAG", "MainActivityViewModel.init()")
    }

    /**
     * The [LiveData] instance used to publish the result of [fetchQuoteOfDay].
     */
    val quoteOfDay: LiveData<QuoteOfDayDTO> = savedState.getLiveData(VIEW_STATE)

    /**
     * The [LiveData] instance used to publish errors that occur while fetching the quote of day
     */
    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    /**
     * Asynchronous operation to fetch the quote of the day from the remote server. The operation
     * result (if successful) is published to the associated [LiveData] instance, [quoteOfDay].
     */
    fun fetchQuoteOfDay() {
        val app = getApplication<QuoteOfDayApplication>()
        val repo = QuoteOfDayRepository(app.quoteOfDayService, app.historyDB.getHistoryQuoteDao())
        viewModelScope.launch {
            val result = repo.fetchQuoteOfDay()
            savedState.set(VIEW_STATE, result)
        }
    }
}