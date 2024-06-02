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
                CardSource.Wikipedia,
                "https://upload.wikimedia.org/wikipedia/en/thumb/8/80/Wikipedia-logo-v2.svg/1200px-Wikipedia-logo-v2.svg.png"
            )
        }
        return card
    }
}