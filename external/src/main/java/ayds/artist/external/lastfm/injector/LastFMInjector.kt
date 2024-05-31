package ayds.artist.external.lastfm.injector

import ayds.artist.external.lastfm.data.*
import ayds.artist.external.lastfm.data.LastFMToBiographyResolverImpl

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object LastFMInjector {

    lateinit var lastFMService: LastFMService
    lateinit var lastFMProxy: LastFMProxy

    fun init(): LastFMProxy {
        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val lastFMToBiographyResolver = LastFMToBiographyResolverImpl()

        lastFMService = LastFMServiceImpl(lastFMAPI, lastFMToBiographyResolver)
        lastFMProxy = LastFMProxyImpl(lastFMService)
        return lastFMProxy
    }

}