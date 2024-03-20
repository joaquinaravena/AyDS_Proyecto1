package ayds.artist.external.newyorktimes.data

interface NYTimesService {
    fun getArtistInfo(artistName: String?): NYTimesArticle
}
