package ayds.songinfo.presentation

import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelper
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
import org.junit.Test
import io.mockk.mockk
import junit.framework.TestCase.assertEquals


class ArtistBiographyDescriptionHelperTest{

    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

    @Test
    fun `given a local Artist biography should return the description`(){
        val artistBiography : ArtistBiography = mockk()

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)

        val expected = "<html><div width=400><font face=\"arial\">[*]${artistBiography.biography}</font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `given a non local Artist biography should return the description`(){
        val artistBiography : ArtistBiography = mockk()

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)

        val expected = "<html><div width=400><font face=\"arial\">${artistBiography.biography}</font></div></html>"

        assertEquals(expected, result)
    }
}