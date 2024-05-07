package ayds.songinfo.moredetails.fulllogic.model.repository

import ayds.songinfo.moredetails.fulllogic.model.entities.Biography
import ayds.songinfo.moredetails.fulllogic.model.entities.Biography.EmptyBiography
import ayds.songinfo.moredetails.fulllogic.model.entities.Biography.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.model.repository.external.ArtistBiographyService
import ayds.songinfo.moredetails.fulllogic.model.repository.local.ArtistBiographyLocalStorage

//TODO Verificar si es necesario conseguir la biografia por la id y agregarlo como atributo
interface BiographyRepository {
    fun getBiographyByTerm(term: String): Biography
    fun getBiographyByName(artistName: String): Biography
}

internal class BiographyRepositoryImpl(
    private val biographyLocalStorage: ArtistBiographyLocalStorage,
    private val biographyService: ArtistBiographyService
): BiographyRepository{

    override fun getBiographyByTerm(term: String): Biography {
        var artistBiography = biographyLocalStorage.getBiographyByTerm(term)

        when {
            artistBiography != null -> markBiographyAsLocal(artistBiography)
            else -> {
                try {
                    artistBiography = biographyService.getArticle(term)
                    if(artistBiography.biography.isNotEmpty())
                        biographyLocalStorage.insertArtist(artistBiography)
                } catch (e: Exception) {
                    artistBiography = null
                }
            }
        }

        return artistBiography ?: EmptyBiography
    }

    override fun getBiographyByName(artistName: String) = biographyLocalStorage.getBiographyByName(artistName) ?: EmptyBiography

    private fun ArtistBiography.isSavedBiography() = biographyLocalStorage.getBiographyByName(artistName) != null

    private fun markBiographyAsLocal(biography: ArtistBiography){
        biography.isLocallyStored = true
    }
}
