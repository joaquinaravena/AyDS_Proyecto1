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

    override fun getArtistInfo(artistName: String): Card {
        val dbCard = otherInfoLocalStorage.getArticle(artistName)

        val card: Card

        if (dbCard != null) {
            card = dbCard.apply { markItAsLocal() }
        } else {
            card = lastFMBiographyToCard(lastFMService.getArticle(artistName))
            if (card.text.isNotEmpty()) {
                otherInfoLocalStorage.insertArtist(card)
            }
        }
        return card
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }

    private fun lastFMBiographyToCard(lastFm: LastFMBiography): Card {
        return Card(
            lastFm.artistName,
            lastFm.biography,
            lastFm.url,
            CardSource.LastFM
        )
    }
}
