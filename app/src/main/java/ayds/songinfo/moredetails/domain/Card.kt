package ayds.songinfo.moredetails.domain

data class Card(
    val artistName: String,
    val text: String,
    val url: String,
    val source: CardSource,
    var isLocallyStored: Boolean = false
)

enum class CardSource {
    LastFM,
    Wikipedia,
    NYTimes
}