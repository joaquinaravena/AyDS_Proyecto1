package ayds.songinfo.moredetails.data.repository

import ayds.songinfo.moredetails.data.OtherInfoBroker
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository

internal class OtherInfoRepositoryImpl(
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val otherInfoBroker: OtherInfoBroker,
) : OtherInfoRepository {

    override fun getCard(artistName: String): List<Card> {
        val dbCards = otherInfoLocalStorage.getCard(artistName)

        val cards: List<Card>

        if (dbCards.isNotEmpty()) {
            for (dbCard: Card in dbCards)
                apply { dbCard.markItAsLocal(); }
            cards = dbCards
        } else {
            cards = otherInfoBroker.getListCards(artistName)
            cards.forEach { card -> otherInfoLocalStorage.insertCard(card) }
        }
        return cards
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }
}
