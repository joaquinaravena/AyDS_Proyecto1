package ayds.songinfo.moredetails.data.repository.local

import androidx.room.*

@Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
}

@Entity(primaryKeys = ["artistName", "source"])
data class CardEntity(
    val artistName: String,
    val description: String,
    val infoUrl: String,
    val source: String,
    @TypeConverters(SourceLogoUrlTypeConverter::class)
    val sourceLogoUrl: String
)

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card: CardEntity)

    @Query("SELECT * FROM Cardentity WHERE artistName LIKE :artistName AND source LIKE :source LIMIT 1")
    fun getCardByArtistName(artistName: String, source: String): CardEntity?

}

class SourceLogoUrlTypeConverter {
    @TypeConverter
    fun toSourceLogoUrl(value: String) = SourceLogoUrl.entries.first { it.url == value }

    @TypeConverter
    fun fromSourceLogoUrl(value: SourceLogoUrl) = value.url
}

enum class SourceLogoUrl(val url: String) {
    LastFMAPI("https://cdn.iconscout.com/icon/free/png-256/lastfm-282152.png"),
    Wikipedia("https://upload.wikimedia.org/wikipedia/en/thumb/8/80/Wikipedia-logo-v2.svg/1200px-Wikipedia-logo-v2.svg.png"),
    NYTimes("https://upload.wikimedia.org/wikipedia/commons/7/77/The_New_York_Times_logo.png")
}