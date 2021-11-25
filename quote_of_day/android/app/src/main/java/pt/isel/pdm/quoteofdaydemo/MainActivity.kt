package pt.isel.pdm.quoteofdaydemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import pt.isel.pdm.quoteofdaydemo.about.AboutActivity
import pt.isel.pdm.quoteofdaydemo.common.LoggingActivity
import pt.isel.pdm.quoteofdaydemo.common.QuoteDTO
import pt.isel.pdm.quoteofdaydemo.databinding.ActivityMainBinding
import pt.isel.pdm.quoteofdaydemo.history.HistoryActivity

/**
 * The main application screen, where the day's quote is displayed.
 */
class MainActivity : LoggingActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModels()

    /**
     * Sets up the screen's user experience
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.quoteOfDay.observe(this) { displayQuote(it.quote) }
        viewModel.error.observe(this) { displayError() }

        binding.fetchButton.setOnClickListener {
            displayFetching()
            viewModel.fetchQuoteOfDay()
        }
    }

    /**
     * Sets up the screen's menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    /**
     * Sets up the screen's menu behaviour
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            R.id.history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Helper method used to show that the quote of day is being fetched
     */
    private fun displayFetching() {
        binding.quoteText.text = resources.getText(R.string.fetching_message)
        binding.quoteAuthor.text = ""
    }

    /**
     * Helper method used to show the day's quote
     */
    private fun displayQuote(quote: QuoteDTO) {
        binding.quoteText.text = quote.text
        binding.quoteAuthor.text = quote.author
    }

    /**
     * Helper method used do display an error, if one has occured while fetching the day's quote.
     */
    private fun displayError() {
        Toast.makeText(this, R.string.get_quote_error, Toast.LENGTH_LONG).show()
    }
}