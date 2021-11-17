package pt.isel.pdm.quoteofdaydemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class LoggingActivity : AppCompatActivity() {

    init {
        Log.v(APP_TAG, "init() ${javaClass.simpleName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(APP_TAG, "onCreate() ${javaClass.simpleName}")
    }

    override fun onStart() {
        super.onStart()
        Log.v(APP_TAG, "onStart() ${javaClass.simpleName}")
    }


    override fun onStop() {
        super.onStop()
        Log.v(APP_TAG, "onStop() ${javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(APP_TAG, "onDestroy() ${javaClass.simpleName}")
    }
}