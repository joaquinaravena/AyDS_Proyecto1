package ayds.artist.external.lastfm.data

import ayds.artist.external.Card
import ayds.artist.external.CardSource

class LastFMProxyImpl(
    private val lastFMService: LastFMService
):LastFMProxy {
    override fun getCard(artistName: String): Card {
        var article = lastFMService.getArticle(artistName)
        val card = Card(
            article.artistName,
            article.biography,
            article.articleUrl,
            CardSource.LastFM
        )
        return card
    }
}