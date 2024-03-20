package ayds.artist.external.newyorktimes.data

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response

private const val PROP_RESPONSE = "response"
private const val WEB_URL = "web_url"
private const val DOCS = "docs"

interface NYTimesToArtistResolver {
    fun getURL(response: Response<String>): String
    fun generateFormattedResponse(response: Response<String>, nameArtist: String?): String?
}

class NYTimesToArtistResolverImpl : NYTimesToArtistResolver {
    private fun getJson(callResponse: Response<String>): JsonObject {
        val gson = Gson()
        return gson.fromJson(callResponse.body(), JsonObject::class.java)
    }

    private fun getAsJsonObject(response: JsonObject): JsonElement? {
        return response["docs"].asJsonArray[0].asJsonObject["abstract"]
    }

    private fun artistInfoAbstractToString(abstract: JsonElement, nameArtist: String?): String {
        return abstract.asString.replace("\\n", "\n")
    }

    override fun getURL(response: Response<String>): String {
        val jsonResponse = generateResponse(response)
        return jsonResponse[DOCS].asJsonArray[0].asJsonObject[WEB_URL].asString
    }

    private fun generateResponse(response: Response<String>): JsonObject {
        val jObj = getJson(response)
        return jObj[PROP_RESPONSE].asJsonObject
    }

    override fun generateFormattedResponse(response: Response<String>, nameArtist: String?): String? {
        val jsonResponse = generateResponse(response)
        val abstract = getAsJsonObject(jsonResponse)
        return if (abstract == null)
            null
        else
            artistInfoAbstractToString(abstract, nameArtist)
    }

}