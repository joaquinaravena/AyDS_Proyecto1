package ayds.artist.external.wikipedia.data

import ayds.artist.external.Card
import ayds.artist.external.CardSource


class WikipediaProxyImpl(
    private val wikipediaTrackService: WikipediaTrackService
): WikipediaProxy{
    override fun getCard(artistName: String): Card? {
        val article = wikipediaTrackService.getInfo(artistName)
        val card = article?.let {
            Card(
                artistName,
                it.description,
                it.wikipediaURL,
                CardSource.Wikipedia
            )
        }
        return card
    }
}