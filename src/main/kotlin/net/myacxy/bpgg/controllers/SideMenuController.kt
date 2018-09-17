package net.myacxy.bpgg.controllers

import com.jfoenix.controls.JFXDecorator
import javafx.scene.Scene
import javafx.stage.FileChooser
import javafx.stage.Stage
import net.myacxy.bpgg.models.GameEvent
import net.myacxy.bpgg.models.SideMenuEvent
import net.myacxy.bpgg.views.PresentationView
import tornadofx.*

class SideMenuController : Controller() {

    private val gameController: GameController by inject()
    private val presentationScope = Scope()

    fun onNavigationEvent(event: SideMenuEvent) = when (event) {
        SideMenuEvent.ChoosePicture -> onChoosePicture()
        SideMenuEvent.Present -> onPresent()
        SideMenuEvent.RevealPicture -> onRevealPicture()
    }

    private fun onChoosePicture() {
        val dialogTitle = messages["action_choose_picture"]
        val fileDescription = messages["descr_picture_file"]
        val files = chooseFile(dialogTitle, arrayOf(FileChooser.ExtensionFilter(fileDescription, FILE_EXTENSIONS_PICTURE_CHOOSER)))
        val pathToFile = files.firstOrNull()?.let { PREFIX_FILE_PATH.plus(it.absolutePath) }
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

    private companion object {
        const val PREFIX_FILE_PATH = "file:///"
        val FILE_EXTENSIONS_PICTURE_CHOOSER = listOf("*.jpg", "*.png")
    }

}
