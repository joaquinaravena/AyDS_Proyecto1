package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.os.Bundle
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import ayds.songinfo.moredetails.fulllogic.model.repository.local.ArticleDatabase
import ayds.songinfo.moredetails.fulllogic.model.repository.external.LastFMAPI
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

private const val ARTICLE_BD_NAME = "database-article"
private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"


class OtherInfoWindow : Activity() {

    private lateinit var articleDataBase: ArticleDatabase

    private lateinit var lastFMAPI: LastFMAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initArticleDataBase()
        initLastFMAPI()
        getArtistInfoAsync()
    }

    private fun initArticleDataBase() {
        articleDataBase =
            databaseBuilder(this, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()
    }

    private fun initLastFMAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        lastFMAPI = retrofit.create(LastFMAPI::class.java)
    }

    private fun getArtistInfoAsync() {
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun getArtistInfo() {
        val artistBiography = getArtistBiographyRepository()
        updateUI(artistBiography)
    }

    companion object {
        const val ARTIST_NAME_EXTRA: String = "artistName"
    }

    private fun getArtistName() = intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")
}
