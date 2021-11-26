package pt.isel.pdm.quoteofdaydemo.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.quoteofdaydemo.common.QuoteDTO
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayDTO
import pt.isel.pdm.quoteofdaydemo.databinding.ActivityHistoryBinding

/**
 * The screen used to display the list of daily quotes stored locally.
 */
class HistoryActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<HistoryActivityViewModel>()

    /**
     * Sets up the screen look and behaviour
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.quoteList.layoutManager = LinearLayoutManager(this)

        // Get the list of quotes, if we haven't fetched it yet
        (viewModel.history ?: viewModel.loadHistory()).observe(this) {
            binding.quoteList.adapter = HistoryAdapter(it)
        }
    }
}

