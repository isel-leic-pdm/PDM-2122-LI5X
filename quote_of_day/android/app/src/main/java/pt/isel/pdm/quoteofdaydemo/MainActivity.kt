package pt.isel.pdm.quoteofdaydemo

import android.os.Bundle
import android.view.View
import pt.isel.pdm.quoteofdaydemo.databinding.ActivityMainBinding

fun View.myPostDelayed(delayInMillis: Long, runnable: () -> Unit) {
    this.postDelayed(runnable, delayInMillis)
}

class MainActivity : LoggingActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fetchButton.setOnClickListener { view: View ->
            binding.myView.isEnabled = false
            binding.root.myPostDelayed(3000) {
                binding.myView.isEnabled = true
            }
        }
    }
}