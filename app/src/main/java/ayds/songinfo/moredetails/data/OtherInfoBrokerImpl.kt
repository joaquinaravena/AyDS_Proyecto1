package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.domain.Card
import java.util.ArrayList

internal class OtherInfoBrokerImpl(
    private val cardsProxy: List<CardProxy>

    ) : OtherInfoBroker {
    override fun getListCards(artistName: String): List<Card> {
        val list = ArrayList<Card>()
        for (proxy in cardsProxy) {
            val card = proxy.getCard(artistName)
            if (card != null) {
                list.add(card)
            }
        }
        return list
    }
}