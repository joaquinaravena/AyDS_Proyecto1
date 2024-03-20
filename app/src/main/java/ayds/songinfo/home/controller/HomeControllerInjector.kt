package ayds.songinfo.home.controller

import ayds.songinfo.home.model.HomeModelInjector
import ayds.songinfo.home.view.HomeView

object HomeControllerInjector {

    fun onViewStarted(homeView: HomeView) {
        ayds.songinfo.home.controller.HomeControllerImpl(HomeModelInjector.getHomeModel()).apply {
            setHomeView(homeView)
        }
    }
}