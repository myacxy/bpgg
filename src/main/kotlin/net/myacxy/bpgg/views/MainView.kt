package net.myacxy.bpgg.views

import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.effect.BoxBlur
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.MainController
import net.myacxy.bpgg.models.GameEvent
import tornadofx.View
import tornadofx.action
import tornadofx.get
import tornadofx.objectBinding

class MainView : View() {

    override val root: AnchorPane by fxml("/MainView.fxml")

    private val mainController: MainController by inject()
    private val gameController: GameController by inject()
    private val tfPlayer1: TextField by fxid("tf_main_player1")
    private val tfPlayer2: TextField by fxid("tf_main_player2")
    private val btnChoosePicture: Button by fxid("btn_main_choose_picture")
    private val paneRight: Pane by fxid("pane_main_right")
    private val ivCurrentPicture: ImageView by fxid("iv_main_current_picture")
    private val btnRevealPicture: Button by fxid("btn_main_reveal_picture")

    init {
        with(root) {
            title = messages["title_app"]
        }
        with(tfPlayer1) {
            textProperty().bindBidirectional(gameController.player1.name)
        }
        with(tfPlayer2) {
            textProperty().bindBidirectional(gameController.player2.name)
        }
        with(btnChoosePicture) {
            action { gameController.onGameEvent(GameEvent.Unblur(mainController.choosePicture())) }
        }
        with(ivCurrentPicture) {
            imageProperty().bind(gameController.currentPathOfGuessablePictureProperty.objectBinding { path ->
                path?.let { Image(it, true) }
            })
            effectProperty().bind(gameController.currentBlurProperty.objectBinding { blur ->
                blur?.toDouble()?.let { BoxBlur(it, it, 10) }
            })
            fitWidthProperty().bind(paneRight.widthProperty())
            fitHeightProperty().bind(paneRight.heightProperty())
        }
        with(btnRevealPicture) {
            action { gameController.onGameEvent(GameEvent.Reveal) }
        }
    }

}
