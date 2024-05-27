package ayds.songinfo.moredetails.data.repository.local

import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource

interface OtherInfoLocalStorage {
    fun getArticle(artistName: String): Card?
    fun insertArtist(card: Card)
}

internal class OtherInfoLocalStorageImpl(
    private val cardDatabase: CardDatabase,
) : OtherInfoLocalStorage {

    override fun getArticle(artistName: String): Card? {
        val artistEntity = cardDatabase.CardDao().getCardByArtistName(artistName, "LastFMAPI")
        return artistEntity?.let {
            Card(artistName, artistEntity.description, artistEntity.infoUrl, CardSource.valueOf(artistEntity.source))
        }
    }

    override fun insertArtist(card: Card) {
        cardDatabase.CardDao().insertCard(
            CardEntity(
                card.artistName, card.text, card.url, card.source.toString(), "https://cdn.iconscout.com/icon/free/png-256/lastfm-282152.png"
            )
        )
    }
}