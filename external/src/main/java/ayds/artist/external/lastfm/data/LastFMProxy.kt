package ayds.artist.external.lastfm.data

import ayds.artist.external.Card

interface LastFMProxy {
    fun getCard(artistName: String): Card
}