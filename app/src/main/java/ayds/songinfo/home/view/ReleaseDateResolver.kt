package ayds.songinfo.home.view;

import ayds.songinfo.home.model.entities.Song;


interface ReleaseDateResolverFactory {
    fun getReleaseDateResolver(song: Song.SpotifySong): ReleaseDateResolver
}

class ReleaseDateResolverFactoryImpl : ReleaseDateResolverFactory {
    override fun getReleaseDateResolver(song: Song.SpotifySong) =
        when (song.releaseDatePrecision) {
            "day" -> ReleaseDateDayResolver(song)
            "month" -> ReleaseDateMonthResolver(song)
            "year" -> ReleaseDateYearResolver(song)
            else -> ReleaseDateDefaultResolver(song)
        }
}

interface ReleaseDateResolver {
    val song: Song.SpotifySong
    fun getReleaseDate(): String
}

internal class ReleaseDateDayResolver(override val song: Song.SpotifySong) : ReleaseDateResolver{
    override fun getReleaseDate(): String {
        val dateParts = song.releaseDate.split("-")
        val year = dateParts[0]
        val month = dateParts[1]
        val day = dateParts[2]
        return "$day/$month/$year"
    }
}

internal class ReleaseDateMonthResolver (override val song: Song.SpotifySong) : ReleaseDateResolver{
    override fun getReleaseDate(): String {
        val dateParts = song.releaseDate.split("-")
        val year = dateParts[0]
        val month = dateParts[1]
        return "${month.toInt().monthToString()}, $year"
    }

    private fun Int.monthToString(): String {
        return when (this) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> ""
        }
    }
}

internal class ReleaseDateYearResolver(override val song : Song.SpotifySong) : ReleaseDateResolver{
    override fun getReleaseDate(): String {
        val year = song.releaseDate.split("-").first()
        return year + if (leapYear(year.toInt())) " (leap year)" else "(not a leap year)"
    }
    private fun leapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }
}
internal class ReleaseDateDefaultResolver(override val song : Song.SpotifySong): ReleaseDateResolver{
    override fun getReleaseDate(): String {
        return song.releaseDate
    }
}
