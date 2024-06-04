package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.CardSource

data class CardUiState(
    val artistName: String,
    val contentHtml: String,
    val url: String,
    val source: CardSource,
    val imageUrl: String
)