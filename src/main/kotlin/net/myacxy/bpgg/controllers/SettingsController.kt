package net.myacxy.bpgg.controllers

import javafx.beans.property.SimpleIntegerProperty
import javafx.stage.FileChooser
import tornadofx.*

class SettingsController : Controller() {

    val minimumBlurProperty = SimpleIntegerProperty(20)
    var minimumBlur by minimumBlurProperty

    val maximumBlurProperty = SimpleIntegerProperty(63)
    var maximumBlur by maximumBlurProperty

    fun choosePictures(): List<String> {
        val dialogTitle = messages["action_choose_pictures"]
        val fileDescription = messages["descr_picture_files"]
        val files = chooseFile(dialogTitle, arrayOf(FileChooser.ExtensionFilter(fileDescription, FILE_EXTENSIONS_PICTURE_CHOOSER)), FileChooserMode.Multi)
        return files.map { PREFIX_FILE_PATH.plus(it.absolutePath) }
    }

    private companion object {
        const val PREFIX_FILE_PATH = "file:///"
        val FILE_EXTENSIONS_PICTURE_CHOOSER = listOf("*.jpg", "*.png")
    }

}
