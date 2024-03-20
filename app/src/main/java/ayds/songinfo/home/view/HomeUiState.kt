package ayds.songinfo.home.view

data class HomeUiState(
    val songId: String = "",
    val searchTerm: String = "",
    val songDescription: String = "",
    val songImageUrl: String = DEFAULT_IMAGE,
    val songUrl: String = "",
    val actionsEnabled: Boolean = false,
) {

    companion object {
        const val DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Lisbon_%2836831596786%29_%28cropped%29.jpg/800px-Lisbon_%2836831596786%29_%28cropped%29.jpg"
    }
}