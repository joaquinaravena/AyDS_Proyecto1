package ayds.songinfo.home.model.repository.local.spotify.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {

    @Update
    fun updateSong(song: SongEntity)

    @Insert
    fun insertSong(song: SongEntity)

    @Query("SELECT * FROM songentity WHERE term LIKE :term LIMIT 1")
    fun getSongByTerm(term: String): SongEntity?

    @Query("SELECT * FROM songentity WHERE id LIKE :id LIMIT 1")
    fun getSongById(id: String): SongEntity?
}