package ayds.songinfo.moredetails.fulllogic.model.repository.external

/*
// poner en implementacion de repository external
    private fun getArticleFromService(artistName: String): Biography.ArtistBiography {
        var biography = Biography.ArtistBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            biography = getArtistBioFromExternalData(callResponse.body(), artistName)
        }  catch (e1: IOException) {
            e1.printStackTrace()
        }
        return biography
    }

    private fun getArtistBioFromExternalData(serviceData: String?, artistName: String): Biography.ArtistBiography {
        val gson = Gson()
        val jobj = gson.fromJson(serviceData, JsonObject::class.java)
        val artist = jobj["artist"].asJsonObject
        val bio = artist["bio"].asJsonObject
        val extract = bio["content"]
        val url = artist["url"]
        val text = extract?.asString ?: "No Results"

        return Biography.ArtistBiography(artistName, text, url.asString)
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
 */
