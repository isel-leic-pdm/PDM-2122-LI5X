package pt.isel.pdm.quoteofdaydemo.history

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.room.*
import pt.isel.pdm.quoteofdaydemo.common.APP_TAG
import java.util.concurrent.Executors

/**
 * The data type that represents data stored in the "history_quote" table of the DB
 */
@Entity(tableName = "history_quote")
data class QuoteEntity(
    @PrimaryKey val id: String,
    val author: String,
    val content: String
)

/**
 * The abstraction containing the supported data access operations. The actual implementation is
 * provided by the Room compiler. We can have as many DAOs has our design mandates.
 */
@Dao
interface HistoryQuoteDao {
    @Insert
    fun insert(quote: QuoteEntity)

    @Delete
    fun delete(quote: QuoteEntity)

    @Query("SELECT * FROM history_quote ORDER BY id DESC LIMIT 100")
    fun getAll(): List<QuoteEntity>

    @Query("SELECT * FROM history_quote ORDER BY id DESC LIMIT :count")
    fun getLast(count: Int): List<QuoteEntity>
}

/**
 * The abstraction that represents the DB itself. It is also used as a DAO factory: one factory
 * method per DAO.
 */
@Database(entities = [QuoteEntity::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun getHistoryQuoteDao(): HistoryQuoteDao
}

/**
 * The executor used to execute data access operations
 */
private val ioThreadPool = Executors.newSingleThreadExecutor()

/**
 * Dispatches the execution of [asyncAction] on the appropriate thread pool.
 * This is a teaching tool. It will soon be discarded.
 * The [asyncAction] result is published by calling, IN THE MAIN THREAD, the received callbacks.
 */
fun <T> doAsyncAndCallback(asyncAction: () -> T, success: (T) -> Unit, error: (Throwable) -> Unit) {
    Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: asyncDataAccess")
    val mainHandler = Handler(Looper.getMainLooper())
    ioThreadPool.submit {
        Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: executing action (DB access)")
        try {
            val result = asyncAction()
            mainHandler.post {
                Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: executing success callback")
                success(result)
            }
        }
        catch (ex: Exception) {
            mainHandler.post {
                error(ex)
            }
        }
    }
    Log.v(APP_TAG, "Thread ${Thread.currentThread().name}: asyncDataAccess returned")
}

/**
 * Dispatches the execution of [action] on the appropriate thread pool.
 * This is a teaching tool. It will soon be discarded.
 */
fun doAsync(action: () -> Unit) = ioThreadPool.submit(action)
