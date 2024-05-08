package ayds.songinfo.moredetails.fulllogic.model.repository.local

import ayds.songinfo.moredetails.fulllogic.model.entities.Biography.ArtistBiography

interface ArtistBiographyLocalStorage {

    fun insertArtist(artistBiography: ArtistBiography)

    fun getBiographyByTerm(term: String): ArtistBiography?

    fun getBiographyByName(artistName: String): ArtistBiography?

}