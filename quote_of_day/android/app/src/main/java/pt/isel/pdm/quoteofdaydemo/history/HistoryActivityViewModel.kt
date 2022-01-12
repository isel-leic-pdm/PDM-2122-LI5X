package pt.isel.pdm.quoteofdaydemo.history

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.isel.pdm.quoteofdaydemo.common.*

/**
 * The actual execution host behind the quote history screen (i.e. the [HistoryActivity]).
 */
class HistoryActivityViewModel(application: Application): AndroidViewModel(application) {

    private val historyDao : HistoryQuoteDao by lazy {
        getApplication<QuoteOfDayApplication>().historyDB.getHistoryQuoteDao()
    }

    /**
     * Holds a [LiveData] with the list of quotes
     */
    val quotesHistory: LiveData<List<QuoteOfDayDTO>> = liveData {
        val quotes = historyDao.getAll().map {
            QuoteOfDayDTO(date = it.id, quote = QuoteDTO(it.author, it.content))
        }
        emit(quotes)
    }
}
