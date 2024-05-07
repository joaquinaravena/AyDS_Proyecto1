package ayds.songinfo.moredetails.fulllogic.model

import android.content.Context
import androidx.room.Room
import ayds.songinfo.home.model.repository.SongRepository
import ayds.songinfo.home.model.repository.SongRepositoryImpl
import ayds.songinfo.home.model.repository.external.spotify.SpotifyTrackService
import ayds.songinfo.home.model.repository.local.spotify.SpotifyLocalStorage
import ayds.songinfo.home.model.repository.local.spotify.room.SongDatabase
import ayds.songinfo.home.model.repository.local.spotify.room.SpotifyLocalStorageRoomImpl
import ayds.songinfo.moredetails.fulllogic.view.MoreDetailsView
import ayds.songinfo.home.model.repository.external.spotify.SpotifyInjector

object MoreDetailsModelInjector {

    private lateinit var moreDetailsModel : MoreDetailsModel

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView) {

        // ver cambiar este repository, es copiado del mvc de home
        val dataBase = Room.databaseBuilder(
            moreDetailsView as Context,
            SongDatabase::class.java, "song-database"
        ).build()

        val spotifyLocalRoomStorage: SpotifyLocalStorage = SpotifyLocalStorageRoomImpl(dataBase)

        val spotifyTrackService: SpotifyTrackService = SpotifyInjector.spotifyTrackService

        val repository: SongRepository =
            SongRepositoryImpl(spotifyLocalRoomStorage, spotifyTrackService)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}