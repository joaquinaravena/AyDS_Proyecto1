package ayds.songinfo.moredetails.data.repository.local

import androidx.room.*
import ayds.artist.external.CardSource

@Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
}

@Entity(primaryKeys = ["artistName", "source"])
data class CardEntity(
    val artistName: String,
    val description: String,
    val infoUrl: String,
    val source: Int,
)

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(cards: CardEntity)

    @Query("SELECT * FROM CardEntity WHERE artistName LIKE :artistName AND source LIKE :source LIMIT 1")
    fun getCardByArtistNameAndSource(artistName: String, source: Int): CardEntity?

}