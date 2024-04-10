package ayds.songinfo.home.model.repository.local.spotify.room

import ayds.songinfo.home.model.entities.Song.SpotifySong
import ayds.songinfo.home.model.repository.local.spotify.SpotifyLocalStorage

internal class SpotifyLocalStorageRoomImpl(
    dataBase: SongDatabase,
) : SpotifyLocalStorage {

    private val songDao: SongDao = dataBase.songDao()

    override fun updateSongTerm(query: String, songId: String) {


    }

    override fun updateSongTerm(query: String, song: SpotifySong) {
        songDao.updateSong(song.toSongEntity(query))
    }

    override fun insertSong(query: String, song: SpotifySong) {
        songDao.insertSong(song.toSongEntity(query))
    }

    override fun getSongByTerm(term: String): SpotifySong? {
        return songDao.getSongByTerm(term)?.toSpotifySong()
    }

    override fun getSongById(id: String): SpotifySong? {
        return songDao.getSongById(id)?.toSpotifySong()
    }

    private fun SpotifySong.toSongEntity(term: String) = SongEntity(
        this.id,
        term,
        this.songName,
        this.artistName,
        this.albumName,
        this.releaseDate,
        this.releaseDatePrecision,
        this.spotifyUrl,
        this.imageUrl
    )

    private fun SongEntity.toSpotifySong() = SpotifySong(
        this.id,
        this.songName,
        this.artistName,
        this.albumName,
        this.releaseDate,
        this.releaseDatePrecision,
        this.spotifyUrl,
        this.imageUrl
    )
}