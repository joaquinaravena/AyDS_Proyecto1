package ayds.songinfo.moredetails.data.repository.local

import ayds.artist.external.Card
import ayds.artist.external.CardSource
import java.util.ArrayList

interface OtherInfoLocalStorage {
    fun getCard(artistName: String): List<Card>
    fun insertCard(card: Card)
}

internal class OtherInfoLocalStorageImpl(
    private val cardDatabase: CardDatabase,
) : OtherInfoLocalStorage {

    override fun getCard(artistName: String): List<Card> {
        val lastFMEntity = cardDatabase.CardDao().getCardByArtistNameAndSource(artistName, CardSource.LastFM)
        val nyEntity =cardDatabase.CardDao().getCardByArtistNameAndSource(artistName, CardSource.NYTimes)
        val wikipediaEntity = cardDatabase.CardDao().getCardByArtistNameAndSource(artistName, CardSource.Wikipedia)
        val list = ArrayList<Card>()
        if (lastFMEntity != null) {
            list.add(Card(lastFMEntity.artistName, lastFMEntity.description, lastFMEntity.infoUrl, CardSource.LastFM))
        }
        if (nyEntity != null) {
            list.add(Card(nyEntity.artistName, nyEntity.description, nyEntity.infoUrl, CardSource.NYTimes))
        }
        if (wikipediaEntity != null) {
            list.add(Card(wikipediaEntity.artistName, wikipediaEntity.description, wikipediaEntity.infoUrl, CardSource.Wikipedia))
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