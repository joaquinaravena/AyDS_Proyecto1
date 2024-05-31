package ayds.songinfo.moredetails.data

import ayds.artist.external.Card

interface Broker {
    fun getListCards(artistName: String): List<Card>
}
