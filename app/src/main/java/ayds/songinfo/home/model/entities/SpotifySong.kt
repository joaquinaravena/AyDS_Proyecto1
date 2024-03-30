package ayds.songinfo.home.model.entities

sealed class Song {
    data class SpotifySong(
        val id: String,
        val songName: String,
        val artistName: String,
        val albumName: String,
        val releaseDate: String,
        val spotifyUrl: String,
        val imageUrl: String,
        var isLocallyStored: Boolean = false
    ) : Song() {

        val releaseDatePrecision: String = when (releaseDate.split("-").size) {
            3 -> "day"   // Year-Month-Day
            2 -> "month" // Year-Month
            1 -> "year"  // Year
            else -> "unknown" // Invalid format
        }
    }

    object EmptySong : Song()
}

