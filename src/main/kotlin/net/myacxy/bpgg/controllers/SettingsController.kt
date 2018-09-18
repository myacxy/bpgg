package net.myacxy.bpgg.controllers

import javafx.beans.property.SimpleIntegerProperty
import javafx.stage.FileChooser
import tornadofx.*

class SettingsController : Controller() {

    val minimumBlurProperty = SimpleIntegerProperty(20)
    var minimumBlur by minimumBlurProperty

    val maximumBlurProperty = SimpleIntegerProperty(63)
    var maximumBlur by maximumBlurProperty

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
