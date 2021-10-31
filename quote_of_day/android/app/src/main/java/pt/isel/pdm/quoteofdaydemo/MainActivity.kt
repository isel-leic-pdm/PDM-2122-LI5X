package pt.isel.pdm.quoteofdaydemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import pt.isel.pdm.quoteofdaydemo.databinding.ActivityMainBinding

class MainActivity : LoggingActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.v("APP_TAG", "quoteText.hashcode = ${binding.quoteText.hashCode()}")

        binding.fetchButton.setOnClickListener {
            Log.v("APP_TAG", "onClick")
            binding.quoteText.text = resources.getText(R.string.fetching_message)
            viewModel.getQuoteOfDay()
        }

        viewModel.quoteOfDay.observe(this) {
            Log.v("APP_TAG", "observer notified")
            binding.quoteText.text = it.text
            binding.quoteAuthor.text = it.author
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}