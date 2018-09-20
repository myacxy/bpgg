package net.myacxy.bpgg.controllers

import net.myacxy.bpgg.views.MainView
import tornadofx.*

class MainController : Controller() {

    private val mainView: MainView by inject()

    fun toggleSettingsMenu() {
        mainView.toggleDrawer()
    }

}
