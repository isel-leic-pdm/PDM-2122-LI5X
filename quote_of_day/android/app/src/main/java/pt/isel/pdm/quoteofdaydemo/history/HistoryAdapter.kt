package pt.isel.pdm.quoteofdaydemo.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayDTO
import pt.isel.pdm.quoteofdaydemo.R

class HistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dayView = itemView.findViewById<TextView>(R.id.day)
    private val authorView = itemView.findViewById<TextView>(R.id.author)

    fun bindTo(quoteOfDayDTO: QuoteOfDayDTO) {
        dayView.text = quoteOfDayDTO.date
        authorView.text = quoteOfDayDTO.quote.author
    }
}

class HistoryAdapter(private val dataSource: List<QuoteOfDayDTO>):
    RecyclerView.Adapter<HistoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_view, parent, false)
        return HistoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bindTo(dataSource[position])
    }

    override fun getItemCount() = dataSource.size
}