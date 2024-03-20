package ayds.songinfo.home.model.repository.external.spotify

import ayds.songinfo.home.model.entities.Song.SpotifySong

interface SpotifyTrackService {

    fun getSong(title: String): SpotifySong?
}