package pt.isel.pdm.quoteofdaydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

const val TAG = "APP_TAG"

abstract class LoggingActivity : AppCompatActivity() {

    init {
        Log.v(TAG, "init()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart()")
    }


    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy()")
    }
}