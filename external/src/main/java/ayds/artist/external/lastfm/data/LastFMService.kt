package ayds.artist.external.lastfm.data

interface LastFMService {
    fun getArticle(artistName: String): LastFMBiography
}