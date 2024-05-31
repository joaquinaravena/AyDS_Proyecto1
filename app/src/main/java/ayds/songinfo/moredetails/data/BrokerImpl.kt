package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.LastFMProxy
import ayds.artist.external.newyorktimes.data.NYTimesProxy
import ayds.artist.external.wikipedia.data.WikipediaProxy
import ayds.artist.external.Card
import java.util.ArrayList

internal class BrokerImpl(
    private val proxyLastFM: LastFMProxy,
    private val proxyNYTimes: NYTimesProxy,
    private val proxyWikipedia: WikipediaProxy,

    ) : Broker {
    override fun getListCards(artistName: String): List<Card> {
        val list = ArrayList<Card>()
        val lastFMCard = proxyLastFM.getCard(artistName)
        val nyTimesCard = proxyNYTimes.getCard(artistName)
        val wikipediaCard = proxyWikipedia.getCard(artistName)
        list.add(lastFMCard)
        if (nyTimesCard != null) {
            list.add(nyTimesCard)
        }
        if (wikipediaCard != null) {
            list.add(wikipediaCard)
        }
        return list
    }
}