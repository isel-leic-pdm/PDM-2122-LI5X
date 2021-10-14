package pt.isel.pdm.quoteofdaydemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.quoteofdaydemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.fetchButton.setOnClickListener {
            it.isEnabled = false
        }
    }
}