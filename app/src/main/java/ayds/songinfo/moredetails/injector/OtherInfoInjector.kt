import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastfm.injector.LastFMInjector
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.data.repository.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.repository.local.CardDatabase
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl


private const val ARTICLE_BD_NAME = "database-article"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {

        val cardDatabase =
            Room.databaseBuilder(context, CardDatabase::class.java, ARTICLE_BD_NAME).build()
        val lastFMService = LastFMInjector.lastFMService
        val articleLocalStorage = OtherInfoLocalStorageImpl(cardDatabase)

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, lastFMService)

        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}