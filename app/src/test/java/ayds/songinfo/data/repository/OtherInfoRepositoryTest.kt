package ayds.songinfo.data.repository

import ayds.artist.external.lastfm.data.LastFMBiography
import ayds.artist.external.lastfm.data.LastFMService
import ayds.songinfo.moredetails.data.repository.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import io.mockk.every
import org.junit.Test
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
class OtherInfoRepositoryTest {
    private val otherInfoLocalStorage: OtherInfoLocalStorage = mockk(relaxUnitFun = true)
    private val lastFmService: LastFMService = mockk()
    private val otherInfoRepository: OtherInfoRepository = OtherInfoRepositoryImpl(otherInfoLocalStorage, lastFmService)

    @Test
    fun `given a local stored article should return artist biography`() {
        val artistBiographyMock = ArtistBiography("artistName", "artistBiography", "url", false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns artistBiographyMock

        val result = otherInfoRepository.getArtistInfo("artistName")
        assertEquals(true, result.isLocallyStored)
        assertEquals(artistBiographyMock, result)
    }

    @Test
    fun `given a non local stored article and non empty should return artist biography`() {
        val artistBiographyMock = ArtistBiography("artistName", "artistBiography", "url", false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns null
        every { lastFmService.getArticle("artistName") } returns LastFMBiography("artistName", "artistBiography", "url")


        val result = otherInfoRepository.getArtistInfo("artistName")

        assertEquals(false, result.isLocallyStored)
        assertEquals(artistBiographyMock, result)
        verify { otherInfoLocalStorage.insertArtist(artistBiographyMock) }

    }

    @Test
    fun `given a non local stored article and empty should return artist biography`() {
        val artistBiographyMock = ArtistBiography("artistName", "", "url", false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns null
        every { lastFmService.getArticle("artistName") } returns LastFMBiography("artistName", "", "url")

        val result = otherInfoRepository.getArtistInfo("artistName")

        assertEquals(false, result.isLocallyStored)
        assertEquals(artistBiographyMock, result)
        verify(inverse = true) { otherInfoLocalStorage.insertArtist(artistBiographyMock) }
    }
}


