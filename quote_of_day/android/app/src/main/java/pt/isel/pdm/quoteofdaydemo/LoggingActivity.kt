package pt.isel.pdm.quoteofdaydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

const val TAG = "APP_TAG"

abstract class LoggingActivity : AppCompatActivity() {

    init {
        Log.v(TAG, "init() ${javaClass.simpleName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate() ${javaClass.simpleName}")
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart() ${javaClass.simpleName}")
    }


    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop() ${javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy() ${javaClass.simpleName}")
    }
}