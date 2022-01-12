package pt.isel.pdm.quoteofdaydemo.history

import androidx.room.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

/**
 * The data type that represents data stored in the "history_quote" table of the DB
 */
@Entity(tableName = "history_quote")
data class QuoteEntity(
    @PrimaryKey val id: String,
    val author: String,
    val content: String,
    val timestamp: Date = Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS))
) {
    fun isTodayQuote(): Boolean =
        timestamp.toInstant().compareTo(Instant.now().truncatedTo(ChronoUnit.DAYS)) == 0
}

/**
 * Contains converters used by the ROOM ORM to map between Kotlin types and MySQL types
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time
}


/**
 * The abstraction containing the supported data access operations. The actual implementation is
 * provided by the Room compiler. We can have as many DAOs has our design mandates.
 */
@Dao
interface HistoryQuoteDao {
    @Insert
    suspend fun insert(quote: QuoteEntity)

    @Delete
    suspend fun delete(quote: QuoteEntity)

    @Query("SELECT * FROM history_quote ORDER BY id DESC LIMIT 100")
    suspend fun getAll(): List<QuoteEntity>

    @Query("SELECT * FROM history_quote ORDER BY id DESC LIMIT :count")
    suspend fun getLast(count: Int): List<QuoteEntity>
}

/**
 * The abstraction that represents the DB itself. It is also used as a DAO factory: one factory
 * method per DAO.
 */
@Database(entities = [QuoteEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun getHistoryQuoteDao(): HistoryQuoteDao
}
