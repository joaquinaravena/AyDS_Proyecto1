package ayds.artist.external.newyorktimes.data

import ayds.artist.external.newyorktimes.data.NYTimesArticle.EmptyArtistDataExternal
import ayds.artist.external.newyorktimes.data.NYTimesArticle.NYTimesArticleWithData
import java.io.IOException

internal class NYTimesServiceImpl(
    private val nyTimesAPI: NYTimesAPI,
    private val nyTimesToArtistResolver: NYTimesToArtistResolver,
) : NYTimesService {

    override fun getArtistInfo(artistName: String?): NYTimesArticle {
        var infoArtist: String? = null
        try {
            infoArtist = nyTimesToArtistResolver.generateFormattedResponse(getInfoFromAPI(artistName), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return if(infoArtist == null){
            EmptyArtistDataExternal
        }
        else{
            val response = getInfoFromAPI(artistName)
            NYTimesArticleWithData(artistName, infoArtist, nyTimesToArtistResolver.getURL(response))
        }
    }

    private fun getInfoFromAPI(artistName: String?) = nyTimesAPI.getArtistInfo(artistName).execute()

}