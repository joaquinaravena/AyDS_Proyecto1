package ayds.songinfo.presentation

import ayds.artist.external.Card
import ayds.artist.external.CardSource
import ayds.songinfo.moredetails.presentation.CardDescriptionHelper
import ayds.songinfo.moredetails.presentation.CardDescriptionHelperImpl
import org.junit.Test
import junit.framework.TestCase.assertEquals


class CardDescriptionHelperTest{

    private val cardDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()

    @Test
    fun `given a local Artist biography should return the description`(){
        val card = Card("Artist", "Biography", "url", CardSource.LastFM,true)

        val result = cardDescriptionHelper.getDescription(card)
        val expected = "<html><div width=400><font face=\"arial\">[*]${card.text}</font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `given a non local Artist biography should return the description`(){
        val card = Card("Artist", "Biography", "url", CardSource.LastFM,false)

        val result = cardDescriptionHelper.getDescription(card)
        val expected = "<html><div width=400><font face=\"arial\">${card.text}</font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `on double slash should return br`(){
        val card = Card("Artist", "Biography\\n", "url", CardSource.LastFM,false)

        val result = cardDescriptionHelper.getDescription(card)
        val expected = "<html><div width=400><font face=\"arial\">Biography<br></font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `on simple slash should return br`(){
        val card = Card("Artist", "Biography\n", "url", CardSource.LastFM,false)

        val result = cardDescriptionHelper.getDescription(card)
        val expected = "<html><div width=400><font face=\"arial\">Biography<br></font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `should remove apostrophes`(){
        val card = Card("Artist", "Biography's", "url", CardSource.LastFM,false)

        val result = cardDescriptionHelper.getDescription(card)
        val expected = "<html><div width=400><font face=\"arial\">Biography s</font></div></html>"

        assertEquals(expected, result)
    }

    @Test
    fun `should bold and uppercase artist name`(){
        val card = Card("Artist", "Biography Artist", "url", CardSource.LastFM,false)

        val result = cardDescriptionHelper.getDescription(card)
        val expected = "<html><div width=400><font face=\"arial\">Biography <b>ARTIST</b></font></div></html>"

        assertEquals(expected, result)
    }

}