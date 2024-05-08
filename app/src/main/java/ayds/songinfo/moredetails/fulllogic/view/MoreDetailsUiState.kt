package ayds.songinfo.moredetails.fulllogic.view


data class MoreDetailsUiState(
    val artistName: String = "",
    val biography: String = "",
    val articleUrl: String = "",
    val actionsEnabled : Boolean = false
)