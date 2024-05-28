package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.domain.Card

interface OtherInfoPresenter {
    val cardObservable: Observable<CardUiState>
    fun updateCard(artistName: String)

}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val cardDescriptionHelper: CardDescriptionHelper
) : OtherInfoPresenter {

    override val cardObservable = Subject<CardUiState>()

    override fun updateCard(artistName: String) {
        val card = repository.getCard(artistName)

        val uiState = card.toUiState()

        cardObservable.notify(uiState)
    }

    private fun Card.toUiState() = CardUiState(
        artistName,
        cardDescriptionHelper.getDescription(this),
        url
    )
}