package ayds.artist.external.newyorktimes.injector

import ayds.artist.external.newyorktimes.data.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LINK_API_NYTIMES = "https://api.nytimes.com/svc/search/v2/"

object NYTimesInjector {
    private val nyTimesAPI = getNYTimesAPI()
    private val nyTimesToArtistResolver: NYTimesToArtistResolver = NYTimesToArtistResolverImpl()
    lateinit var nyTimesService: NYTimesService

    fun init() {
        nyTimesService = NYTimesServiceImpl(nyTimesAPI, nyTimesToArtistResolver)
    }

    private fun getNYTimesAPI(): NYTimesAPI {
        val nyTimesAPIRetrofit = Retrofit.Builder()
            .baseUrl(LINK_API_NYTIMES)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return nyTimesAPIRetrofit.create(NYTimesAPI::class.java)
    }
}