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
            CardSource.LastFM,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
        )
        return card
    }
}