package ayds.artist.external

data class Card(
    val artistName: String,
    val text: String,
    val url: String,
    val source: CardSource,
    var isLocallyStored: Boolean = false
)

enum class CardSource {
    LastFM,
    NYTimes,
    Wikipedia
}

enum class SourceLogoUrl(val url: String) {
    LastFMAPI("https://cdn.iconscout.com/icon/free/png-256/lastfm-282152.png"),
    Wikipedia("https://upload.wikimedia.org/wikipedia/en/thumb/8/80/Wikipedia-logo-v2.svg/1200px-Wikipedia-logo-v2.svg.png"),
    NYTimes("https://upload.wikimedia.org/wikipedia/commons/7/77/The_New_York_Times_logo.png")
}