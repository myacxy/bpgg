package net.myacxy.bpgg.controllers

import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.chooseFile
import tornadofx.get

class MainController : Controller() {

    fun choosePicture(): String? {
        val dialogTitle = messages["action_choose_picture"]
        val fileDescription = messages["descr_picture_file"]
        val files = chooseFile(dialogTitle, arrayOf(FileChooser.ExtensionFilter(fileDescription, FILE_EXTENSIONS_PICTURE_CHOOSER)))
        return files.firstOrNull()?.let { PREFIX_FILE_PATH.plus(it.absolutePath) }
    }

    private companion object {
        const val PREFIX_FILE_PATH = "file:///"
        val FILE_EXTENSIONS_PICTURE_CHOOSER = listOf("*.jpg", "*.png")
    }

}
