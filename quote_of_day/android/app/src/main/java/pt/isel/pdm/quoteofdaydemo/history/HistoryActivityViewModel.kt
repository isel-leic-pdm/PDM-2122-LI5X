package pt.isel.pdm.quoteofdaydemo.history

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pt.isel.pdm.quoteofdaydemo.common.*

/**
 * The actual execution host behind the quote history screen (i.e. the [HistoryActivity]).
 */
class HistoryActivityViewModel(application: Application): AndroidViewModel(application) {

    /**
     * Holds a [LiveData] with the list of quotes, or null if it has not yet been requested by
     * the [HistoryActivity] through a call to [loadHistory]
     */
    var history: LiveData<List<QuoteOfDayDTO>>? = null
        private set

    private val historyDao : HistoryQuoteDao by lazy {
        getApplication<QuoteOfDayApplication>().historyDB.getHistoryQuoteDao()
    }

    /**
     * Gets the quotes list (history) from the DB.
     */
    fun loadHistory(): LiveData<List<QuoteOfDayDTO>> {
        val publish = MutableLiveData<List<QuoteOfDayDTO>>()
        history = publish
        callbackAfterAsync(
            asyncAction = {
                Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: Getting history from local DB")
                historyDao.getAll().map {
                    QuoteOfDayDTO(date = it.id, quote = QuoteDTO(it.author, it.content))
                }
            },
            callback = { result ->
                Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: mapping results")
                result.onSuccess { publish.value = it }
                result.onFailure { publish.value = emptyList() }
            }
        )
        return publish
    }
}
