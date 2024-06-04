package ayds.songinfo.presentation

import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.presentation.CardDescriptionHelper
import ayds.songinfo.moredetails.presentation.CardUiState
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest {

    private val repository: OtherInfoRepository = mockk(relaxUnitFun = true)
    private val cardDescriptionHelper: CardDescriptionHelper = mockk(relaxUnitFun = true)

    private val otherInfoPresenter: OtherInfoPresenter =
        OtherInfoPresenterImpl(repository, cardDescriptionHelper)

    @Test
    fun `given an Artist who is in the repository should show the artist info`() {
        val card = Card("artistName", "biography", "url", CardSource.LastFM)
        every { repository.getCard("artistName") } returns card
        every { cardDescriptionHelper.getDescription(card) } returns "description"

        val artistBiographyTester: (CardUiState) -> Unit = mockk(relaxed = true)

        otherInfoPresenter.cardObservable.subscribe(artistBiographyTester)
        otherInfoPresenter.updateCard("artistName")

        val result= CardUiState("artistName", "description", "url")
        verify { artistBiographyTester(result) }
    }
}