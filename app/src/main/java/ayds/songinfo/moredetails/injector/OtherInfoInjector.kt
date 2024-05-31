package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastfm.injector.LastFMInjector
import ayds.songinfo.moredetails.data.Broker
import ayds.songinfo.moredetails.data.BrokerImpl
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.data.repository.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.repository.local.CardDatabase
import ayds.songinfo.moredetails.presentation.CardDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl
import ayds.artist.external.newyorktimes.injector.NYTimesInjector
import ayds.artist.external.wikipedia.injector.WikipediaInjector


private const val ARTICLE_BD_NAME = "database-article"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {

        val lfmProxy = LastFMInjector.init()
        val nytProxy = NYTimesInjector.init()
        val wProxy = WikipediaInjector.init()

        val cardDatabase =
            Room.databaseBuilder(context, CardDatabase::class.java, ARTICLE_BD_NAME).build()

        val articleLocalStorage = OtherInfoLocalStorageImpl(cardDatabase)

        val broker : Broker = BrokerImpl(
            lfmProxy,
            nytProxy,
            wProxy
        )

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, broker)

        val artistBiographyDescriptionHelper = CardDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}