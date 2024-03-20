package ayds.artist.external.newyorktimes.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesAPI {
    @GET("articlesearch.json?api-key=fFnIAXXz8s8aJ4dB8CVOJl0Um2P96Zyx")
    fun getArtistInfo(@Query("q") artist: String?): Call<String>
}
