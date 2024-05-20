package ayds.songinfo.moredetails.data.repository.local

import ayds.songinfo.moredetails.domain.ArtistBiography

interface OtherInfoLocalStorage {
    fun getArticle(artistName: String): ArtistBiography?
    fun insertArtist(artistBiography: ArtistBiography)
}

internal class OtherInfoLocalStorageImpl(
    private val cardDatabase: CardDatabase,
) : OtherInfoLocalStorage {

    override fun getArticle(artistName: String): ArtistBiography? {
        val artistEntity = cardDatabase.CardDao().getArticleByArtistName(artistName, "LastFMAPI")
        return artistEntity?.let {
            ArtistBiography(artistName, artistEntity.description, artistEntity.infoUrl)
        }
    }

    override fun insertArtist(artistBiography: ArtistBiography) {
        cardDatabase.CardDao().insertArticle(
            CardEntity(
                artistBiography.artistName, artistBiography.biography, artistBiography.articleUrl, "LastFMAPI", "https://cdn.iconscout.com/icon/free/png-256/lastfm-282152.png"
            )
        )
    }
}