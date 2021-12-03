package pt.isel.pdm.quoteofdaydemo.history

import androidx.room.*

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
