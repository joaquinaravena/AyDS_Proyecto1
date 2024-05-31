package ayds.artist.external.wikipedia.data

import ayds.artist.external.Card

interface WikipediaProxy {
    fun getCard(artistName: String): Card?
}