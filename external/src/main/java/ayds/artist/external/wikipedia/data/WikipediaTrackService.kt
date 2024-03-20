package ayds.artist.external.wikipedia.data

interface WikipediaTrackService {
    fun getInfo(artistName: String): WikipediaArticle?
}