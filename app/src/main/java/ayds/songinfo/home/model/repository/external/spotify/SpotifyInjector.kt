package ayds.songinfo.home.model.repository.external.spotify

import ayds.songinfo.home.model.repository.external.spotify.tracks.*

object SpotifyInjector {

    val spotifyTrackService: SpotifyTrackService = SpotifyTrackInjector.spotifyTrackService
}