package ayds.songinfo.moredetails.fulllogic.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.fulllogic.model.entities.Biography
import ayds.songinfo.moredetails.fulllogic.model.repository.BiographyRepository

interface MoreDetailsModel {

    val biographyObservable: Observable<Biography>

    fun searchBiography(term: String)

    fun getBiography(artistName: String): Biography

}

internal class MoreDetailsModelImpl(private val repository: BiographyRepository) : MoreDetailsModel {

    override val biographyObservable = Subject<Biography>()

    override fun searchBiography(term: String) {
        repository.getBiographyByTerm(term).let {
            biographyObservable.notify(it)
        }
    }

    override fun getBiography(artistName: String): Biography = repository.getBiographyByName(artistName)
}