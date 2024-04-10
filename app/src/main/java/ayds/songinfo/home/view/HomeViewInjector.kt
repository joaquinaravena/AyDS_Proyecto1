package ayds.songinfo.home.view

import ayds.songinfo.home.controller.HomeControllerInjector
import ayds.songinfo.home.model.HomeModelInjector

object HomeViewInjector {

    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(ReleaseDateResolverFactoryImpl())

    fun init(homeView: HomeView) {
        HomeModelInjector.initHomeModel(homeView)
        HomeControllerInjector.onViewStarted(homeView)
    }
}