package ayds.artist.external.newyorktimes.data

import ayds.artist.external.Card

interface NYTimesProxy {
    fun getCard(artistName: String): Card?
}