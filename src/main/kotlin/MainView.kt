import javafx.scene.layout.VBox
import javafx.scene.text.TextAlignment
import tornadofx.*

class MainView : View() {

    private val controller: MainController by inject()

    override val root = VBox()

    init {
        with(root) {
            title = messages["title_app"]
            style {
                prefWidth = 1280.pt
                prefHeight = 720.pt
                padding = CssBox(16.pt, 16.pt, 16.pt, 16.pt)
                fontFamily = "Fira Sans"
            }
            this += imageview(controller.pathOfPictureProperty)
            this += button(messages["action_choose_picture"].toUpperCase()) {
                style {
                    minWidth = 64.pt
                    minHeight = 36.pt
                    padding = CssBox(0.pt, 16.pt, 0.pt, 16.pt)
                    textAlignment = TextAlignment.CENTER
                    fontSize = 12.pt
                }
                action { controller.choosePicture() }
            }
        }
    }

}
