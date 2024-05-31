package ayds.songinfo.moredetails.domain

import ayds.artist.external.Card

interface OtherInfoRepository {
    fun getCard(artistName: String): List<Card>
}