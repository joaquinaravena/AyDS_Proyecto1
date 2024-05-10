package ayds.songinfo.moredetails.presentation

data class ArtistBiographyUiState(
    val artistName: String,
    val infoHtml: String,
    val articleUrl: String,
    val imageUrl: String =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
)