import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.*

class MainController : Controller() {

    val pathOfPictureProperty = SimpleStringProperty(PREFIX_FILE_PATH)
    var pathOfPicture by pathOfPictureProperty

    fun choosePicture() {
        val dialogTitle = messages["action_choose_picture"]
        val fileDescription = messages["descr_picture_file"]
        val files = chooseFile(dialogTitle, arrayOf(FileChooser.ExtensionFilter(fileDescription, FILE_EXTENSIONS_PICTURE_CHOOSER)))
        pathOfPicture = PREFIX_FILE_PATH.plus(files.firstOrNull()?.absolutePath ?: "")
    }

    private companion object {
        const val PREFIX_FILE_PATH = "file:///"
        val FILE_EXTENSIONS_PICTURE_CHOOSER = listOf("*.jpg", "*.png")
    }

}
