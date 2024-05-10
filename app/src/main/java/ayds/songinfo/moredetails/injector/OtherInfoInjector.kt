import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.data.repository.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.repository.external.LastFMAPI
import ayds.songinfo.moredetails.data.repository.external.LastFMToArtistBiographyResolverImpl
import ayds.songinfo.moredetails.data.repository.external.OtherInfoServiceImpl
import ayds.songinfo.moredetails.data.repository.local.ArticleDatabase
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


private const val ARTICLE_BD_NAME = "database-article"
private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {

        val articleDatabase =
            Room.databaseBuilder(context, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val lastFMToArtistBiographyResolver = LastFMToArtistBiographyResolverImpl()
        val otherInfoService = OtherInfoServiceImpl(lastFMAPI, lastFMToArtistBiographyResolver)
        val articleLocalStorage = OtherInfoLocalStorageImpl(articleDatabase)

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, otherInfoService)

        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}