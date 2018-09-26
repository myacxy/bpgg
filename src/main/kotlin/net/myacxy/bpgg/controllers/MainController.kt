package net.myacxy.bpgg.controllers

import com.jfoenix.controls.JFXDecorator
import javafx.scene.Scene
import javafx.stage.Stage
import net.myacxy.bpgg.models.GameEvent
import net.myacxy.bpgg.views.MainView
import net.myacxy.bpgg.views.PresentationView
import tornadofx.*

class MainController : Controller() {

    private val gameController: GameController by inject()
    private val settingsController: SettingsController by inject()

    private val mainView: MainView by inject()

    private val presentationScope = Scope()

    fun choosePictures() {
        val filePaths = settingsController.choosePictures()
        gameController.onGameEvent(GameEvent.NewPictures(filePaths))
    }

    fun present() {
        val view = find<PresentationView>(presentationScope)
        Stage().apply {
            scene = Scene(JFXDecorator(this, view.root))
            scene.stylesheets.setAll(primaryStage.scene.stylesheets)
        }.show()
    }

    fun toggleSettingsMenu() {
        mainView.toggleDrawer()
    }
}
