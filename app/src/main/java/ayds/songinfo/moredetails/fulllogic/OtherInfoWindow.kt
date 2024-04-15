package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.*

class OtherInfoWindow : Activity() {
    private var textPane1: TextView? = null
    private var dataBase: ArticleDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_other_info)

        textPane1 = findViewById(R.id.textPane1)

        open(intent.getStringExtra(ARTIST_NAME_EXTRA))
    }

    private fun getArtistInfo(artistName: String?) {
        val lastFMAPI = getLastFMAPI()

        Thread {
            val article = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
            var text = ""

            if (article != null) {
                text = "[*]" + article.biography
                addListenerToOpenUrlButton1(article.articleUrl)
            } else {
                val callResponse: Response<String>
                try {
                    callResponse = lastFMAPI.getArtistInfo(artistName).execute()

                    val gson = Gson()
                    val jobj = gson.fromJson(callResponse.body(), JsonObject::class.java)
                    val artist = jobj["artist"].asJsonObject
                    val bio = artist["bio"].asJsonObject
                    val extract = bio["content"]
                    val url = artist["url"]

                    when (extract) {
                        null -> {
                            text = "No Results"
                        }
                        else -> {
                            text = extract.asString.replace("\\n", "\n")
                            text = textToHtml(text, artistName)
                            Thread { dataBase!!.ArticleDao().insertArticle(ArticleEntity(artistName, text, url.asString)) }
                                .start()
                        }
                    }
                    addListenerToOpenUrlButton1(url.asString)
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
            loadText(text)
        }.start()
    }

    private fun loadText(text: String) {
        runOnUiThread {
            Picasso.get().load(IMAGE_URL).into(findViewById<View>(R.id.imageView1) as ImageView)
            textPane1!!.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        }
    }

    private fun getLastFMAPI(): LastFMAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val lastFMAPI = retrofit.create(LastFMAPI::class.java)
        return lastFMAPI
    }

    private fun addListenerToOpenUrlButton1(urlArticle: String) {
        findViewById<View>(R.id.openUrlButton1).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlArticle))
            startActivity(intent)
        }
    }

    private fun open(artist: String?) {
        dataBase = databaseBuilder(this, ArticleDatabase::class.java, "database-name-thename").build()
        getArtistInfo(artist)
    }

    companion object {
        const val ARTIST_NAME_EXTRA: String = "artistName"
        const val BASE_URL: String = "https://ws.audioscrobbler.com/2.0/"
        const val IMAGE_URL: String =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

        fun textToHtml(text: String, term: String?): String {
            val textWithBold = text
                .replace("'", " ")
                .replace("\n", "<br>")
                .replace("(?i)$term".toRegex(), "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>")

            return "<html><div width=400>" + "<font face=\"arial\">" +
                    textWithBold + "</font></div></html>"
        }
    }
}
