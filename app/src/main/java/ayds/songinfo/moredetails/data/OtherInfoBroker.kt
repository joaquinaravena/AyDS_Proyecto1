package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.domain.Card

interface OtherInfoBroker {
    fun getListCards(artistName: String): List<Card>
}
