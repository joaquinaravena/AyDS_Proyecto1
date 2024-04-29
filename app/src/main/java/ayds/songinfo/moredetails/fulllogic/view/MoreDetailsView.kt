package ayds.songinfo.moredetails.fulllogic.view

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.R
import ayds.songinfo.home.view.*
import ayds.songinfo.moredetails.fulllogic.model.MoreDetailsModel
import ayds.songinfo.moredetails.fulllogic.model.MoreDetailsModelInjector

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun viewFullArticle()
}

class MoreDetailsViewActivity : Activity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var moreDetailsModel: MoreDetailsModel

    private lateinit var viewFullArticleButton: Button

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
        viewFullArticleButton = findViewById(R.id.viewFullArticleButton)
    }

    private fun initListeners() {
        viewFullArticleButton.setOnClickListener { notifyViewFullArticleAction() }
    }

    private fun notifyViewFullArticleAction() {
        onActionSubject.notify(MoreDetailsUiEvent.ViewFullArticle)
    }

    private fun updateDisabledActionsState() {
        uiState = uiState.copy(actionsEnabled = false)
    }

    private fun initObservers() {

    }

    private fun updateViewFullArticleState() {
        enableActions(uiState.actionsEnabled)
    }

    private fun enableActions(enable: Boolean) {
        runOnUiThread {
            viewFullArticleButton.isEnabled = enable
        }
    }
}