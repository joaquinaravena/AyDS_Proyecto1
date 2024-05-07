package ayds.songinfo.moredetails.fulllogic.model.repository.external

import ayds.songinfo.moredetails.fulllogic.model.entities.Biography.ArtistBiography

interface ArtistBiographyService {
    fun getArticle(artistName: String): ArtistBiography

    fun getArtistBiography(artistName: String): ArtistBiography
}

