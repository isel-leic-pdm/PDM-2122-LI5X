package pt.isel.pdm.quoteofdaydemo.common

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors

/**
 * The executor used to execute blocking IO operations
 */
private val ioExecutor = Executors.newSingleThreadExecutor()

private fun <T> executeAndCollectResult(asyncAction: () -> T): Result<T> =
    try { Result.success(asyncAction()) }
    catch (e: Exception) { Result.failure(e) }

/**
 * Dispatches the execution of [asyncAction] on the appropriate thread pool.
 * This is a teaching tool. It will soon be discarded.
 * The [asyncAction] result is published by calling, IN THE MAIN THREAD, the received [callback]
 */
fun <T> callbackAfterAsync(callback: (Result<T>) -> Unit, asyncAction: () -> T) {
    val mainHandler = Handler(Looper.getMainLooper())
    ioExecutor.submit {
        val result = executeAndCollectResult(asyncAction)
        mainHandler.post {
            callback(result)
        }
    }
}

/**
 * Dispatches the execution of [asyncAction] on the appropriate thread pool.
 * This is a teaching tool. It will soon be discarded.
 * The [asyncAction] result is published to the returned [LiveData] instance
 */
fun <T> publishInLiveDataAfterAsync(asyncAction: () -> T): LiveData<Result<T>> {
    val toPublish = MutableLiveData<Result<T>>()
    ioExecutor.submit {
        val result = executeAndCollectResult(asyncAction)
        toPublish.postValue(result)
    }
    return toPublish
}
