package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXProgressBar
import javafx.animation.Interpolator
import javafx.beans.value.ChangeListener
import javafx.scene.CacheHint
import javafx.scene.control.Label
import javafx.scene.effect.GaussianBlur
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.SettingsController
import org.controlsfx.glyphfont.FontAwesome
import org.controlsfx.glyphfont.Glyph
import tornadofx.*

class PresentationView : View() {

    override val root: AnchorPane by fxml("/PresentationView.fxml")

    private val settingsController: SettingsController by inject(DefaultScope)
    private val gameController: GameController by inject(DefaultScope)
    private val gaussianBlur: GaussianBlur
    private val shouldRevealListener: ChangeListener<Boolean>

    init {
        gaussianBlur = GaussianBlur().apply { bindProgressToEffectRadius(this) }
        shouldRevealListener = ChangeListener { _, _, reveal ->
            if (!reveal) bindProgressToEffectRadius(gaussianBlur)
            else playRevealAnimation(gaussianBlur)
        }
    }

    override fun onDock() = gameController.shouldRevealProperty.addListener(shouldRevealListener)

    override fun onUndock() = gameController.shouldRevealProperty.removeListener(shouldRevealListener)

    private fun bindProgressToEffectRadius(effect: GaussianBlur) {

        fun getBlurRadius(progress: Double): Double {
            val minimumBlur = settingsController.minimumBlur
            val maximumBlur = settingsController.maximumBlur
            return maximumBlur - (maximumBlur - minimumBlur).div(100.0).times(progress)
        }

        effect.radiusProperty().apply {
            unbind()
            bind(gameController.progressProperty.doubleBinding {
                getBlurRadius(it?.toDouble() ?: 0.0)
            })
        }
    }

    private fun playRevealAnimation(effect: GaussianBlur) = with(effect.radiusProperty()) {
        unbind()
        animate(0, 500.millis, Interpolator.EASE_IN)
    }


    //<editor-fold desc="picture views">
    private val ivCurrentPicture: ImageView by fxid("iv_pv_picture")
    private val pbProgress: JFXProgressBar by fxid("pb_pv_progress")

    init {
        with(ivCurrentPicture) {
            isCache = true
            effect = gaussianBlur
            cacheHintProperty().bind(gameController.shouldRevealProperty.objectBinding {
                it?.run { CacheHint.SPEED } ?: CacheHint.QUALITY
            })
            imageProperty().bind(gameController.pictureProperty.objectBinding { path ->
                path?.let { Image(it, true) }
            })
            fitHeightProperty().bind(root.heightProperty().multiply(0.8))
        }
        with(pbProgress) {
            val progress = gameController.progressProperty.doubleBinding { it?.toDouble()?.div(100.0) ?: 0.0 }
            progressProperty().bind(progress)
        }
    }
    //</editor-fold>

    //<editor-fold desc="player1 views">
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

    //<editor-fold desc="player2 views">
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
