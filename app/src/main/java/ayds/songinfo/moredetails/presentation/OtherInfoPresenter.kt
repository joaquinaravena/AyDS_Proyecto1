package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.domain.ArtistBiography

interface OtherInfoPresenter {
    val artistBiographyObservable: Observable<ArtistBiographyUiState>
    fun getArtistInfo(artistName: String)

}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper
) : OtherInfoPresenter {

    override val artistBiographyObservable = Subject<ArtistBiographyUiState>()

    override fun getArtistInfo(artistName: String) {
        val artistBiography = repository.getArtistInfo(artistName)

        val uiState = artistBiography.toUiState()

        artistBiographyObservable.notify(uiState)
    }

    private fun ArtistBiography.toUiState() = ArtistBiographyUiState(
        artistName,
        artistBiographyDescriptionHelper.getDescription(this),
        articleUrl
    )
}