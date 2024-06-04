package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastfm.injector.LastFMInjector
import ayds.artist.external.newyorktimes.injector.NYTimesInjector
import ayds.artist.external.wikipedia.data.WikipediaTrackService
import ayds.artist.external.wikipedia.injector.WikipediaInjector
import ayds.songinfo.moredetails.data.*
import ayds.songinfo.moredetails.data.repository.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.repository.local.CardDatabase
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.presentation.CardDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl


private const val ARTICLE_BD_NAME = "database-article"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {

        LastFMInjector.init()
        NYTimesInjector.init()
        WikipediaInjector.init()

        val cardDatabase =
            Room.databaseBuilder(context, CardDatabase::class.java, ARTICLE_BD_NAME).build()

        val articleLocalStorage = OtherInfoLocalStorageImpl(cardDatabase)

        val lastFMProxy = LastFMProxy(LastFMInjector.lastFMService)
        val wikipediaProxy = WikipediaProxy(WikipediaInjector.wikipediaTrackService)
        val nyTimesProxy = NYTimesProxy(NYTimesInjector.nyTimesService)

        val otherInfoBroker : OtherInfoBroker = OtherInfoBrokerImpl(
            listOf(
                lastFMProxy,
                wikipediaProxy,
                nyTimesProxy
            )
        )

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, otherInfoBroker)

        val artistBiographyDescriptionHelper = CardDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}