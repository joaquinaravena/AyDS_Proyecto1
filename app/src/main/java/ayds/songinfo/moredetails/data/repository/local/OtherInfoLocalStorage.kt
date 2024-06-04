package ayds.songinfo.moredetails.data.repository.local

import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource
import java.util.ArrayList

interface OtherInfoLocalStorage {
    fun getCard(artistName: String): List<Card>
    fun insertCard(card: Card)
}

internal class OtherInfoLocalStorageImpl(
    private val cardDatabase: CardDatabase,
) : OtherInfoLocalStorage {

    override fun getCard(artistName: String): List<Card> {
        val lastFMEntity = cardDatabase.CardDao().getCardByArtistNameAndSource(artistName, CardSource.LastFM.ordinal)
        val nyEntity =cardDatabase.CardDao().getCardByArtistNameAndSource(artistName, CardSource.NYTimes.ordinal)
        val wikipediaEntity = cardDatabase.CardDao().getCardByArtistNameAndSource(artistName, CardSource.Wikipedia.ordinal)

        val list = ArrayList<Card>()
        if (lastFMEntity != null) {
            list.add(Card(lastFMEntity.artistName, lastFMEntity.description, lastFMEntity.infoUrl, CardSource.LastFM, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"))
        }
        if (nyEntity != null) {
            list.add(Card(nyEntity.artistName, nyEntity.description, nyEntity.infoUrl, CardSource.NYTimes,"https://upload.wikimedia.org/wikipedia/commons/7/77/The_New_York_Times_logo.png"))
        }
        if (wikipediaEntity != null) {
            list.add(
                Card(wikipediaEntity.artistName, wikipediaEntity.description, wikipediaEntity.infoUrl,
                    CardSource.Wikipedia, "https://upload.wikimedia.org/wikipedia/en/thumb/8/80/Wikipedia-logo-v2.svg/1200px-Wikipedia-logo-v2.svg.png")
            )
        }

        return list
    }

    override fun insertCard(card: Card) {
        cardDatabase.CardDao().insertCard(
            CardEntity(
                card.artistName, card.text, card.url, card.source.ordinal)
        )
    }
}