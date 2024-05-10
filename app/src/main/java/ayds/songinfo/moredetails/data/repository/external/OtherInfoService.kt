package ayds.songinfo.moredetails.data.repository.external

import ayds.songinfo.moredetails.domain.ArtistBiography
import java.io.IOException

interface OtherInfoService {
    fun getArticle(artistName: String): ArtistBiography
}

internal class OtherInfoServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver
) : OtherInfoService {

    override fun getArticle(artistName: String): ArtistBiography {

        var artistBiography = ArtistBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            artistBiography = lastFMToArtistBiographyResolver.map(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistBiography
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

}