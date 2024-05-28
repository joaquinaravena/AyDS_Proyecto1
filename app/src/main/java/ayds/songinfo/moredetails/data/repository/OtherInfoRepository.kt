package ayds.songinfo.moredetails.data.repository

import ayds.artist.external.lastfm.data.LastFMBiography
import ayds.artist.external.lastfm.data.LastFMService
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource
import ayds.songinfo.moredetails.domain.OtherInfoRepository

internal class OtherInfoRepositoryImpl(
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val lastFMService: LastFMService,
) : OtherInfoRepository {

    override fun getCard(artistName: String): Card {
        val dbCard = otherInfoLocalStorage.getCard(artistName)

        val card: Card

        if (dbCard != null) {
            card = dbCard.apply { markItAsLocal() }
        } else {
            card = lastFMService.getArticle(artistName).toCard()
            if (card.text.isNotEmpty()) {
                otherInfoLocalStorage.insertCard(card)
            }
        }
        return card
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }

    private fun LastFMBiography.toCard() =
        Card(artistName, biography, articleUrl,
            CardSource.LastFM)

}
