package ayds.songinfo.home.model.repository.local.spotify

import ayds.songinfo.home.model.entities.Song.SpotifySong

interface SpotifyLocalStorage {

    fun updateSongTerm(query: String, songId: String)

    fun updateSongTerm(query: String, song: SpotifySong)

    fun insertSong(query: String, song: SpotifySong)

    fun getSongByTerm(term: String): SpotifySong?

    fun getSongById(id: String): SpotifySong?
}