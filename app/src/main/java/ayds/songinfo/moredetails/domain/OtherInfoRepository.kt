package ayds.songinfo.moredetails.domain

interface OtherInfoRepository {
    fun getArtistInfo(artistName: String): Card
}