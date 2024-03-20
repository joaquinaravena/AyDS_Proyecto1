package ayds.songinfo.moredetails.fulllogic

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun ArticleDao(): ArticleDao
}

@Entity
data class ArticleEntity(
    @PrimaryKey
    val artistName: String,
    val biography: String,
    val articleUrl: String,
)

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM Articleentity WHERE artistName LIKE :artistName LIMIT 1")
    fun getArticleByArtistName(artistName: String): ArticleEntity?

}