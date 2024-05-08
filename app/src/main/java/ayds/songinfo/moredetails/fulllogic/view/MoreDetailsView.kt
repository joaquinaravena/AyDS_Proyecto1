package ayds.songinfo.moredetails.fulllogic.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.R
import ayds.songinfo.moredetails.fulllogic.model.MoreDetailsModel
import ayds.songinfo.moredetails.fulllogic.model.MoreDetailsModelInjector
import ayds.songinfo.moredetails.fulllogic.model.entities.Biography
import com.squareup.picasso.Picasso
import java.util.*

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun viewFullArticle()
}

private const val LASTFM_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

class MoreDetailsViewActivity : Activity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var moreDetailsModel: MoreDetailsModel

    private lateinit var openUrlButton: Button
    private lateinit var articleTextView: TextView
    private lateinit var lastFMAPImageView: ImageView

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()


    override fun viewFullArticle() {
        onActionSubject.notify(MoreDetailsUiEvent.ViewFullArticle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initModule()
        initProperties()
        initListeners()
        initObservers()

    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
    }

    private fun initProperties() {
        articleTextView = findViewById(R.id.textPane1)
        lastFMAPImageView = findViewById(R.id.lastFMImageView)
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initListeners() {
        openUrlButton.setOnClickListener {
            viewFullArticle()
        }
    }

    private fun initObservers() {
        moreDetailsModel.biographyObservable
            .subscribe { value -> updateArtistBiography(value) }
    }

    private fun updateArtistBiography(artistBiography: Biography) {
        updateUiState(artistBiography)
        updateOpenUrlButton()
        updateArticleTextView()
        updateLastFMLogo()
        updateOpenUrlState()
    }

    private fun updateUiState(biography: Biography) {
        when (biography) {
            is Biography.ArtistBiography -> updateBiographyUiState(biography)
            is Biography.EmptyBiography -> updateNoResultsUiState()
        }
    }

    private fun updateBiographyUiState(biography: Biography.ArtistBiography) {
        uiState = uiState.copy(
            artistName = biography.artistName,
            biography = biography.biography,
            articleUrl = biography.articleUrl,
            actionsEnabled = true
        )
    }

    private fun updateNoResultsUiState() {
        uiState = uiState.copy(
            artistName = "",
            biography = "No results found",
            articleUrl = "",
            actionsEnabled = false
        )
    }
    private fun updateOpenUrlButton() {
        runOnUiThread {
            navigateToUrl()
        }
    }

    private fun navigateToUrl() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(uiState.articleUrl))
        startActivity(intent)
    }

    private fun updateArticleTextView() {
        val text = uiState.biography.replace("\\n", "\n")
        articleTextView.text = Html.fromHtml(textToHtml(text, uiState.artistName))
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

    private fun updateOpenUrlState() {
        enableActions(uiState.actionsEnabled)
    }

    private fun enableActions(enable: Boolean) {
        runOnUiThread {
            openUrlButton.isEnabled = enable
        }
    }
}