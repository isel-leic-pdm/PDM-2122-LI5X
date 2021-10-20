package pt.isel.pdm.quoteofdaydemo

import android.os.Bundle
import android.util.Log
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
            viewModel.getQuoteOfDay {
                binding.quoteText.text = it
            }
        }
    }
}