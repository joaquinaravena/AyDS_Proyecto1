package ayds.songinfo.home.model.repository.local.spotify.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongEntity(
    @PrimaryKey val id: String,
    val term: String,
    val songName: String,
    val artistName: String,
    val albumName: String,
    val releaseDate: String,
    val spotifyUrl: String,
    val imageUrl: String,
)