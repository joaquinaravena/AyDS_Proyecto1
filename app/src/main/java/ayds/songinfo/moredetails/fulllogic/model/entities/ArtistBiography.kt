package ayds.songinfo.moredetails.fulllogic.model.entities

sealed class Biography {
    data class ArtistBiography(
        val artistName: String,
        val biography: String,
        val articleUrl: String,
        var isLocallyStored: Boolean = false
    ) : Biography()

    object EmptyBiography : Biography()
}
