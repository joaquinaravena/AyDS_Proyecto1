package ayds.artist.external.newyorktimes.data

const val NYT_LOGO_URL =
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"

sealed class NYTimesArticle {

    data class NYTimesArticleWithData(
        val name: String?,
        val info: String?,
        val url: String,
    ): NYTimesArticle()

    object EmptyArtistDataExternal : NYTimesArticle()
}