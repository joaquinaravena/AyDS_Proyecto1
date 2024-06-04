package ayds.songinfo.moredetails.domain

interface OtherInfoRepository {
    fun getCard(artistName: String): List<Card>
}