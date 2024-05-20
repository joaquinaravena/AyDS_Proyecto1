package ayds.songinfo.presentation

import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelper
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
import io.mockk.every
import org.junit.Test
import io.mockk.mockk
import junit.framework.TestCase.assertEquals


class ArtistBiographyDescriptionHelperTest{

    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

    @Test
    fun `given a local Artist biography should return the description`(){
        val artistBiography = ArtistBiography("Artist", "Biography", "url", true)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        val expected = "<html><div width=400><font face=\"arial\">[*]${artistBiography.biography}</font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `given a non local Artist biography should return the description`(){
        val artistBiography = ArtistBiography("Artist", "Biography", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        val expected = "<html><div width=400><font face=\"arial\">${artistBiography.biography}</font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `on double slash should return br`(){
        val artistBiography = ArtistBiography("Artist", "Biography\\n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        val expected = "<html><div width=400><font face=\"arial\">Biography<br></font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `on simple slash should return br`(){
        val artistBiography = ArtistBiography("Artist", "Biography\n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        val expected = "<html><div width=400><font face=\"arial\">Biography<br></font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `should remove apostrophes`(){
        val artistBiography = ArtistBiography("Artist", "Biography's", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        val expected = "<html><div width=400><font face=\"arial\">Biography s</font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `should bold and uppercase artist name`(){
        val artistBiography = ArtistBiography("Artist", "Biography Artist", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        val expected = "<html><div width=400><font face=\"arial\">Biography <b>ARTIST</b></font></div></html>"

        assertEquals(expected, result)
    }

}