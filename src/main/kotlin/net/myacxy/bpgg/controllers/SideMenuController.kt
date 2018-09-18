package net.myacxy.bpgg.controllers

import com.jfoenix.controls.JFXDecorator
import javafx.scene.Scene
import javafx.stage.Stage
import net.myacxy.bpgg.models.GameEvent
import net.myacxy.bpgg.models.SideMenuEvent
import net.myacxy.bpgg.views.PresentationView
import tornadofx.*

class SideMenuController : Controller() {

    private val gameController: GameController by inject()
    private val settingsController: SettingsController by inject()
    private val presentationScope = Scope()

    fun onNavigationEvent(event: SideMenuEvent) = when (event) {
        SideMenuEvent.ChoosePicture -> onChoosePicture()
        SideMenuEvent.Present -> onPresent()
        SideMenuEvent.RevealPicture -> onRevealPicture()
    }

    private fun onChoosePicture() {
        val pathToFile = settingsController.choosePicture()
        gameController.onGameEvent(GameEvent.NewPicture(pathToFile))
    }

    private fun onPresent() {
        Stage().apply {
            scene = Scene(JFXDecorator(this, find<PresentationView>(presentationScope).root))
        }.show()
    }

    private fun onRevealPicture() {
        gameController.onGameEvent(GameEvent.Reveal)
    }

}
