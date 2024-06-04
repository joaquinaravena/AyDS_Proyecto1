package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.moredetails.domain.CardSource
import ayds.songinfo.R
import ayds.songinfo.moredetails.injector.OtherInfoInjector
import com.squareup.picasso.Picasso

class OtherInfoActivity : Activity() {
    private lateinit var cardContentTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var sourceImageView: ImageView


    private lateinit var cardContent1TextView: TextView
    private lateinit var openUrl1Button: Button
    private lateinit var source1ImageView: ImageView

    private lateinit var cardContent2TextView: TextView
    private lateinit var openUrl2Button: Button
    private lateinit var source2ImageView: ImageView

    private lateinit var presenter: OtherInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initViewProperties()
        initPresenter()

        observePresenter()
        getArtistCardAsync()
    }

    private fun initPresenter() {
        OtherInfoInjector.initGraph(this)
        presenter = OtherInfoInjector.presenter
    }

    private fun observePresenter() {
        presenter.cardObservable.subscribe { card ->
            updateUi(card)
        }
    }

    private fun initViewProperties() {
        cardContentTextView = findViewById(R.id.cardContent1TextView)
        openUrlButton = findViewById(R.id.openUrl1Button)
        sourceImageView = findViewById(R.id.source1ImageView)

        cardContent1TextView = findViewById(R.id.cardContent2TextView)
        openUrl1Button = findViewById(R.id.openUrl2Button)
        source1ImageView = findViewById(R.id.source2ImageView)

        cardContent2TextView = findViewById(R.id.cardContent3TextView)
        openUrl2Button = findViewById(R.id.openUrl3Button)
        source2ImageView = findViewById(R.id.source3ImageView)
    }

    private fun getArtistCardAsync() {
        Thread {
            getArtistCard()
        }.start()
    }

    private fun getArtistCard() {
        val artistName = getArtistName()
        presenter.updateCard(artistName)
    }

    private fun updateUi(uiState: CardUiState) {
        runOnUiThread {
            updateOpenUrlButton(uiState.url, uiState.source)
            updateLogo(uiState.imageUrl, uiState.source)
            updateCardText(uiState.contentHtml, uiState.source)
        }
    }

    private fun updateOpenUrlButton(url: String, source: CardSource) {
        when (source) {
            CardSource.LastFM -> openUrlButton.setOnClickListener {
                navigateToUrl(url)
            }
            CardSource.NYTimes -> openUrl1Button.setOnClickListener {
                navigateToUrl(url)
            }
            CardSource.Wikipedia -> openUrl2Button.setOnClickListener {
                navigateToUrl(url)
            }
        }
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun updateLogo(url: String, source: CardSource) {
        when (source) {
            CardSource.LastFM -> Picasso.get().load(url).into(sourceImageView)
            CardSource.NYTimes -> Picasso.get().load(url).into(source1ImageView)
            CardSource.Wikipedia -> Picasso.get().load(url).into(source2ImageView)
        }
    }

    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    private fun updateCardText(infoHtml: String, source: CardSource) {
        when (source) {
            CardSource.LastFM -> cardContentTextView.text = Html.fromHtml(infoHtml)
            CardSource.NYTimes -> cardContent1TextView.text = Html.fromHtml(infoHtml)
            CardSource.Wikipedia -> cardContent2TextView.text = Html.fromHtml(infoHtml)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}