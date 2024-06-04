package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.LastFMBiography
import ayds.artist.external.lastfm.data.LastFMService

class LastFMProxy(
    private val lastFMService: LastFMService
): CardProxy {
    override fun getCard(artistName: String): ayds.songinfo.moredetails.domain.Card {
        val article = lastFMService.getArticle(artistName)
        return article.toCard()
    }

    private fun LastFMBiography.toCard() = ayds.songinfo.moredetails.domain.Card(
        artistName,
        biography,
        articleUrl,
        ayds.songinfo.moredetails.domain.CardSource.LastFM,
        "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
    )

}