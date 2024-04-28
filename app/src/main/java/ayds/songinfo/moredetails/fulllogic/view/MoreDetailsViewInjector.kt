package ayds.songinfo.moredetails.fulllogic.view


import ayds.songinfo.moredetails.fulllogic.model.MoreDetailsModelInjector
import ayds.songinfo.moredetails.fulllogic.presenter.MoreDetailsControllerInjector

object MoreDetailsViewInjector {
    //val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(ReleaseDateResolverFactoryImpl())

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        MoreDetailsControllerInjector.onViewStarted(moreDetailsView)
    }
}