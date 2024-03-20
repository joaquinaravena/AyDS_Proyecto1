package ayds.artist.external.wikipedia.data

import retrofit2.Response

internal class WikipediaTrackServiceImpl(
    private val wikipediaTrackAPI: WikipediaTrackAPI,
    private val wikipediaToInfoResolver: WikipediaToInfoResolver,
) : WikipediaTrackService {

    override fun getInfo(artistName: String): WikipediaArticle? {
        val callResponse = getInfoFromService(artistName)
        return wikipediaToInfoResolver.getInfoFromExternalData(callResponse.body())
    }

    private fun getInfoFromService(artistName: String): Response<String> {
        return wikipediaTrackAPI.getArtistInfo(artistName)
            .execute()
    }
}