package ayds.songinfo.moredetails.fulllogic.model

import ayds.songinfo.home.model.entities.Song
import ayds.songinfo.home.model.repository.SongRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsModel {

    val songObservable: Observable<Song>

}

internal class MoreDetailsModelImpl(private val repository: SongRepository) : MoreDetailsModel {

    override val songObservable = Subject<Song>()

}