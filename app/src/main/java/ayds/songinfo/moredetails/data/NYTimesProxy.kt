package ayds.songinfo.moredetails.data

import ayds.artist.external.newyorktimes.data.NYTimesArticle
import ayds.artist.external.newyorktimes.data.NYTimesService
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource

class NYTimesProxy(
    private val nyTimesService: NYTimesService
): CardProxy {
    override fun getCard(artistName: String): Card {
        val article = nyTimesService.getArtistInfo(artistName) as NYTimesArticle.NYTimesArticleWithData
        return article.toCard()
    }

    private fun NYTimesArticle.NYTimesArticleWithData.toCard() = Card(
        name ?: "Unknown",
        info ?: "Not found",
        url,
        CardSource.NYTimes,
        "https://upload.wikimedia.org/wikipedia/commons/7/77/The_New_York_Times_logo.png"
    )
}