package ayds.songinfo.home.view

import ayds.songinfo.home.model.entities.Song.EmptySong
import ayds.songinfo.home.model.entities.Song
import ayds.songinfo.home.model.entities.Song.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl : SongDescriptionHelper {
    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
               "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release Date: ${getReleaseDate(song)}\n"

            else -> "Song not found."
        }
    }

    private fun getReleaseDate(song: SpotifySong): String {
        val releaseDatePrecision = when (song.releaseDatePrecision) {
            "day" -> song.releaseDate
            "month" -> song.releaseDate.split("-").first() + "-" + song.releaseDate.split("-")[1]
            "year" -> song.releaseDate.split("-").first()
            else -> "unknown"
        }
        return releaseDatePrecision
    }
}