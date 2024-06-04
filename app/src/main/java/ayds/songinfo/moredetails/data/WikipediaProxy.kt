package ayds.songinfo.moredetails.data

import ayds.artist.external.wikipedia.data.WikipediaArticle
import ayds.artist.external.wikipedia.data.WikipediaTrackService


class WikipediaProxy(
    private val wikipediaTrackService: WikipediaTrackService
): CardProxy {
    override fun getCard(artistName: String): ayds.songinfo.moredetails.domain.Card? {
        val article = wikipediaTrackService.getInfo(artistName)
        return article?.toCard(artistName)
    }

    private fun WikipediaArticle.toCard(artistName: String) = ayds.songinfo.moredetails.domain.Card(
        artistName,
        description,
        wikipediaURL,
        ayds.songinfo.moredetails.domain.CardSource.Wikipedia,
        "https://upload.wikimedia.org/wikipedia/en/thumb/8/80/Wikipedia-logo-v2.svg/1200px-Wikipedia-logo-v2.svg.png"
    )
}