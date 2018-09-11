import javafx.scene.layout.VBox
import tornadofx.*

class MainView : View() {

    private val controller: MainController by inject()

    override val root = VBox()

    init {
        with(root) {
            title = messages["title_app"]
            this += imageview(controller.pathOfPictureProperty)
            this += button(messages["action_choose_picture"].toUpperCase()) {
                action { controller.choosePicture() }
            }
        }
    }

}
