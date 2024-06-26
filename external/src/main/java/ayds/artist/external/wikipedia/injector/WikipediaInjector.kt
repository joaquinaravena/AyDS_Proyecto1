package ayds.artist.external.wikipedia.injector


import ayds.artist.external.wikipedia.data.*
import ayds.artist.external.wikipedia.data.JsonToInfoResolver
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val WIKIPEDIA_URL = "https://en.wikipedia.org/w/"

object WikipediaInjector {
    private val wikipediaTrackAPI = getWikipediaAPI()
    private val wikipediaToInfoResolver: WikipediaToInfoResolver = JsonToInfoResolver()
    lateinit var wikipediaTrackService: WikipediaTrackService

    fun init(){
        wikipediaTrackService = WikipediaTrackServiceImpl(
            wikipediaTrackAPI,
            wikipediaToInfoResolver
        )
    }

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(WIKIPEDIA_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private fun getWikipediaAPI() = getRetrofit().create(WikipediaTrackAPI::class.java)
}