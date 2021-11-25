package pt.isel.pdm.quoteofdaydemo.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayDTO
import pt.isel.pdm.quoteofdaydemo.R

/**
 * Implementation of the ViewHolder pattern. Its purpose is to eliminate the need for
 * executing findViewById each time a reference to a view's child is required.
 */
class HistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dayView = itemView.findViewById<TextView>(R.id.day)
    private val authorView = itemView.findViewById<TextView>(R.id.author)

    /**
     * Binds this view holder to the given quote item
     */
    fun bindTo(quoteOfDayDTO: QuoteOfDayDTO) {
        dayView.text = quoteOfDayDTO.date
        authorView.text = quoteOfDayDTO.quote.author
    }
}

/**
 * Adapts items in a data set to RecycleView entries
 */
class HistoryAdapter(private val dataSource: List<QuoteOfDayDTO>):
    RecyclerView.Adapter<HistoryItemViewHolder>() {

    /**
     * Factory method of view holders (and its associated views)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_view, parent, false)
        return HistoryItemViewHolder(view)
    }

    /**
     * Associates (binds) the view associated to [viewHolder] to the item at [position] of the
     * data set to be adapted.
     */
    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bindTo(dataSource[position])
    }

    /**
     * The size of the data set
     */
    override fun getItemCount() = dataSource.size
}