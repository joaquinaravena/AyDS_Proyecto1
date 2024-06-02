package ayds.songinfo.moredetails.data.repository

import ayds.songinfo.moredetails.data.Broker
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorage
import ayds.artist.external.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository

internal class OtherInfoRepositoryImpl(
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val broker: Broker,
) : OtherInfoRepository {

    override fun getCard(artistName: String): List<Card> {
        val dbCards = otherInfoLocalStorage.getCard(artistName)

        val cards: List<Card>

        if (dbCards.isNotEmpty()) {
            for (dbCard: Card in dbCards)
                apply { dbCard.markItAsLocal(); }
            cards = dbCards
        } else {
            cards = broker.getListCards(artistName)
            for(card in cards){
                if (card.text.isNotEmpty()) {
                    otherInfoLocalStorage.insertCard(card)
                }
            }
        }
        return cards
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }
}
