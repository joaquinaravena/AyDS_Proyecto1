package ayds.artist.external.newyorktimes.data

import ayds.artist.external.Card
import ayds.artist.external.CardSource

class NYTimesProxyImpl(
    private val nyTimesService: NYTimesService
): NYTimesProxy {
    override fun getCard(artistName: String): Card? {
        val card = when (val article = nyTimesService.getArtistInfo(artistName)){
            is NYTimesArticle.NYTimesArticleWithData ->{
                Card(
                    article.name ?: "Unknown",
                    article.info ?: "Not found",
                    article.url,
                    CardSource.NYTimes
                )
            }

            else ->{
                null
            }
        }
        return card
    }
}