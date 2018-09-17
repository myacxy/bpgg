package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXProgressBar
import javafx.scene.control.Label
import javafx.scene.effect.BoxBlur
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import net.myacxy.bpgg.controllers.GameController
import org.controlsfx.glyphfont.FontAwesome
import org.controlsfx.glyphfont.Glyph
import tornadofx.*

class PresentationView : View() {

    override val root: AnchorPane by fxml("/PresentationView.fxml")

    private val gameController: GameController by inject(DefaultScope)

    //<editor-fold desc="general">
    private val ivCurrentPicture: ImageView by fxid("iv_pv_picture")
    private val pbProgress: JFXProgressBar by fxid("pb_pv_progress")

    init {
        with(ivCurrentPicture) {
            imageProperty().bind(gameController.currentPathOfGuessablePictureProperty.objectBinding { path ->
                path?.let { Image(it, true) }
            })
            effect = BoxBlur().apply {
                iterations = 10
                widthProperty().bind(gameController.currentBlurProperty)
                heightProperty().bind(gameController.currentBlurProperty)
            }
            fitHeightProperty().bind(root.heightProperty().multiply(0.8))
        }
        with(pbProgress) {

        }
    }
    //</editor-fold>

    //<editor-fold desc="player1">
    private val lName1: Label by fxid("l_pv_name1")
    private val hbScore1: HBox by fxid("hb_pv_score1")
    private val hbCountdown1: HBox by fxid("hb_pv_countdown1")

    init {
        with(lName1) {
            textProperty().bind(gameController.player1.name)
        }
        with(hbScore1) {
            gameController.player1.score.addListener { _, _, newValue ->
                children.clear()
                for (i in 1..newValue.toInt()) {
                    val checkmark = Glyph("FontAwesome", FontAwesome.Glyph.CHECK)
                            .color(Color.LIMEGREEN)
                            .size(40.0)
                    children.add(checkmark)
                }
            }
        }
        with(hbCountdown1) {

        }
    }
    //</editor-fold>

    //<editor-fold desc="player2">
    private val lName2: Label by fxid("l_pv_name2")
    private val hbScore2: HBox by fxid("hb_pv_score2")
    private val hbCountdown2: HBox by fxid("hb_pv_countdown2")

    init {
        with(lName2) {
            textProperty().bind(gameController.player2.name)
        }
        with(hbScore2) {
            gameController.player2.score.addListener { _, _, newValue ->
                children.clear()
                for (i in 1..newValue.toInt()) {
                    val checkmark = Glyph("FontAwesome", FontAwesome.Glyph.CHECK)
                            .color(Color.LIMEGREEN)
                            .size(40.0)
                    children.add(checkmark)
                }
            }
        }
        with(hbCountdown1) {

        }
    }
    //</editor-fold>

}
