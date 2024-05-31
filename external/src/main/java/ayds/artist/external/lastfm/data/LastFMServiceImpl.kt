package ayds.artist.external.lastfm.data

import java.io.IOException

class LastFMServiceImpl (
    private val lastFMAPI: LastFMAPI,
    private val lastFMToBiographyResolver: LastFMToBiographyResolver
) : LastFMService{
    override fun getArticle(artistName: String): LastFMBiography {

        var artistBiography = LastFMBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            artistBiography = lastFMToBiographyResolver.map(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistBiography
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
}