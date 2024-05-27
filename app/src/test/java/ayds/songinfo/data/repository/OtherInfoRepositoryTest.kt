package ayds.songinfo.data.repository

import ayds.artist.external.lastfm.data.LastFMBiography
import ayds.artist.external.lastfm.data.LastFMService
import ayds.songinfo.moredetails.data.repository.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.repository.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource
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
        val cardMock = Card("artistName", "artistBiography", "url", CardSource.LastFM, false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns cardMock

        val result = otherInfoRepository.getArtistInfo("artistName")
        assertEquals(true, result.isLocallyStored)
        assertEquals(cardMock, result)
    }

    @Test
    fun `given a non local stored article and non empty should return artist biography`() {
        val cardMock = Card("artistName", "artistBiography", "url", CardSource.LastFM, false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns null
        every { lastFmService.getArticle("artistName") } returns LastFMBiography("artistName", "artistBiography", "url")


        val result = otherInfoRepository.getArtistInfo("artistName")

        assertEquals(false, result.isLocallyStored)
        assertEquals(cardMock, result)
        verify { otherInfoLocalStorage.insertArtist(cardMock) }

    }

    @Test
    fun `given a non local stored article and empty should return artist biography`() {
        val cardMock = Card("artistName", "", "url", CardSource.LastFM, false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns null
        every { lastFmService.getArticle("artistName") } returns LastFMBiography("artistName", "", "url")

        val result = otherInfoRepository.getArtistInfo("artistName")

        assertEquals(false, result.isLocallyStored)
        assertEquals(cardMock, result)
        verify(inverse = true) { otherInfoLocalStorage.insertArtist(cardMock) }
    }
}


