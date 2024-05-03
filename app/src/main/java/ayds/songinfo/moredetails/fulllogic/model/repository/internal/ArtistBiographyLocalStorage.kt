package ayds.songinfo.moredetails.fulllogic.model.repository.internal

import ayds.songinfo.moredetails.fulllogic.model.entities.ArtistBiography

interface ArtistBiographyLocalStorage {
    fun getArticle(artistName: String): ArtistBiography?

    fun insertArtist(artistBiography: ArtistBiography)

}