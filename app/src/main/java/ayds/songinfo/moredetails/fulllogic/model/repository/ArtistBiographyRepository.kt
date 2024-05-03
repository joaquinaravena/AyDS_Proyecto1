package ayds.songinfo.moredetails.fulllogic.model.repository

import ayds.songinfo.moredetails.fulllogic.model.entities.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.model.repository.external.ArtistBiographyService
import ayds.songinfo.moredetails.fulllogic.model.repository.internal.ArtistBiographyLocalStorage

interface ArtistBiographyRepository {
    fun getArtistBiography(artistName: String): ArtistBiography
    fun getArtistInfo(artistName: String): ArtistBiography
}

internal class ArtistBiographyRepositoryImpl(
    private val artistBiographyLocalStorage: ArtistBiographyLocalStorage,
    private val artistBiographyService: ArtistBiographyService
): ArtistBiographyRepository{

    override fun getArtistBiography(artistName: String): ArtistBiography {
        val dbArticle = artistBiographyLocalStorage.getArticle(artistName)
        val artistBiography: ArtistBiography

        if (dbArticle != null) {
            artistBiography = dbArticle.markItAsLocal()
        } else {
            artistBiography = artistBiographyService.getArticle(artistName)
            if(artistBiography.biography.isNotEmpty())
                artistBiographyLocalStorage.insertArtist(artistBiography)
        }
        return artistBiography
    }

    override fun getArtistInfo(artistName: String): ArtistBiography {
        val artistBiography = getArtistBiography(artistName)
        return artistBiography
    }

    private fun ArtistBiography.markItAsLocal() = copy(biography = "[*]$biography")
}
