package ayds.songinfo.presentation

import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelper
import ayds.songinfo.moredetails.presentation.ArtistBiographyUiState
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest {

    private val repository: OtherInfoRepository = mockk(relaxUnitFun = true)
    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper = mockk(relaxUnitFun = true)

    private val otherInfoPresenter: OtherInfoPresenter =
        OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)

    @Test
    fun `given an Artist who is in the repository should show the artist info`() {
        val artistBiography = ArtistBiography("artistName", "biography", "url")
        every { repository.getArtistInfo("artistName") } returns artistBiography
        every { artistBiographyDescriptionHelper.getDescription(artistBiography) } returns "description"

        val artistBiographyTester: (ArtistBiographyUiState) -> Unit = mockk(relaxed = true)

        otherInfoPresenter.artistBiographyObservable.subscribe(artistBiographyTester)
        otherInfoPresenter.getArtistInfo("artistName")

        val result= ArtistBiographyUiState("artistName", "description", "url")
        verify { artistBiographyTester(result) }
    }
}