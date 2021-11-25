package pt.isel.pdm.quoteofdaydemo.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayDTO

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

    /**
     * Gets the quotes list (history) from the DB.
     */
    fun loadHistory(): LiveData<List<QuoteOfDayDTO>> {
        TODO()
    }
}