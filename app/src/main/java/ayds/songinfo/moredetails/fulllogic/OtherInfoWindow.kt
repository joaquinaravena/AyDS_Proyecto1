package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import ayds.songinfo.moredetails.fulllogic.model.ArticleDatabase
import ayds.songinfo.moredetails.fulllogic.model.ArticleEntity
import ayds.songinfo.moredetails.fulllogic.model.entities.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.model.repository.LastFMAPI
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.*

private const val ARTICLE_BD_NAME = "database-article"
private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
private const val LASTFM_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"


class OtherInfoWindow : Activity() {
    
    private lateinit var articleTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var lastFMAPImageView: ImageView

    private lateinit var articleDataBase: ArticleDatabase

    private lateinit var lastFMAPI: LastFMAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initProperties()
        initArticleDataBase()
        initLastFMAPI()
        getArtistInfoAsync()
    }

    private fun initProperties() {
        articleTextView = findViewById(R.id.textPane1)
        lastFMAPImageView = findViewById(R.id.lastFMImageView)
        openUrlButton = findViewById(R.id.openUrlButton)
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

    private fun getArtistBiographyRepository() : ArtistBiography {
        val artistName = getArtistName()
        val dbArticle = getArticleFromDB(artistName)
        val artistBiography: ArtistBiography

        if (dbArticle != null) {
            artistBiography = dbArticle.markItAsLocal()
        } else {
            artistBiography = getArticleFromService(artistName)
            if(artistBiography.biography.isNotEmpty())
                insertArtistIntoDB(artistBiography)
        }
        return artistBiography
    }

    private fun ArtistBiography.markItAsLocal() = copy(biography = "[*]$biography")

    private fun getArticleFromDB(artistName: String): ArtistBiography? {
        val artistEntity = articleDataBase.ArticleDao().getArticleByArtistName(artistName)
        return artistEntity?.let {
            ArtistBiography(artistName, artistEntity.biography, artistEntity.articleUrl)
        }
    }

    private fun getArticleFromService(artistName: String): ArtistBiography {
        var artistBiography = ArtistBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            artistBiography = getArtistBioFromExternalData(callResponse.body(), artistName)
        }  catch (e1: IOException) {
            e1.printStackTrace()
        }
        return artistBiography
    }

    private fun getArtistBioFromExternalData(serviceData: String?, artistName: String): ArtistBiography {
        val gson = Gson()
        val jobj = gson.fromJson(serviceData, JsonObject::class.java)
        val artist = jobj["artist"].asJsonObject
        val bio = artist["bio"].asJsonObject
        val extract = bio["content"]
        val url = artist["url"]
        val text = extract?.asString ?: "No Results"

        return ArtistBiography(artistName, text, url.asString)
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

    private fun insertArtistIntoDB(artistBiography: ArtistBiography) {
            articleDataBase.ArticleDao().insertArticle(
                ArticleEntity(artistBiography.artistName, artistBiography.biography, artistBiography.articleUrl)
            )
    }

    private fun updateUI(artistBiography: ArtistBiography) {
        runOnUiThread {
            updateOpenUrlButton(artistBiography)
            updateArticleTextView(artistBiography)
            updateLastFMLogo()
        }
    }

    private fun updateOpenUrlButton(artistBiography: ArtistBiography) {
        openUrlButton.setOnClickListener {
            navigateToUrl(artistBiography.articleUrl)
        }
    }

    private fun navigateToUrl(articleUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(articleUrl))
        startActivity(intent)
    }

    private fun updateArticleTextView(artistBiography: ArtistBiography) {
        val text = artistBiography.biography.replace("\\n", "\n")
        articleTextView.text = Html.fromHtml(textToHtml(text, artistBiography.artistName))
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)$term".toRegex(), "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    private fun updateLastFMLogo() {
        Picasso.get().load(LASTFM_IMAGE_URL).into(lastFMAPImageView)
    }

    companion object {
        const val ARTIST_NAME_EXTRA: String = "artistName"
    }

    private fun getArtistName() = intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")
}
