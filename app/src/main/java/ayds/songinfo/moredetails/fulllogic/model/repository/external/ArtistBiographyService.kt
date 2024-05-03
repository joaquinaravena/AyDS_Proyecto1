package ayds.songinfo.moredetails.fulllogic.model.repository.external

import ayds.songinfo.moredetails.fulllogic.model.entities.ArtistBiography

interface ArtistBiographyService {
    fun getArticle(artistName: String): ArtistBiography
}