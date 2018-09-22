package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXProgressBar
import com.jfoenix.effects.JFXDepthManager
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.animation.Interpolator
import javafx.beans.property.BooleanPropertyBase
import javafx.beans.value.ChangeListener
import javafx.css.PseudoClass
import javafx.scene.CacheHint
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.effect.GaussianBlur
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.SettingsController
import net.myacxy.bpgg.models.PlayerModel
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
            opacity = 0.0
            cacheHintProperty().bind(gameController.shouldRevealProperty.objectBinding {
                it?.run { CacheHint.SPEED } ?: CacheHint.QUALITY
            })
            imageProperty().bind(gameController.pictureProperty.objectBinding { path ->
                path?.let { Image(it, true) }
            })
            fitHeightProperty().bind(root.heightProperty().multiply(0.8))
            gameController.showPictureProperty.addListener { _, _, showPicture ->
                val opacity = if (showPicture) 1.0 else 0.0
                val interpolator = if (showPicture) Interpolator.EASE_IN else Interpolator.EASE_OUT
                fade(500.millis, opacity, interpolator, false, true)
            }
        }
        with(pbProgress) {
            val progress = gameController.progressProperty.doubleBinding { it?.toDouble()?.div(100.0) ?: 0.0 }
            progressProperty().bind(progress)
        }
    }
    //</editor-fold>

    //<editor-fold desc="player views">
    private val lName1: Label by fxid("l_pv_name1")
    private val hbScore1: HBox by fxid("hb_pv_score1")
    private val hbCountdown1: HBox by fxid("hb_pv_countdown1")

    private val lName2: Label by fxid("l_pv_name2")
    private val hbScore2: HBox by fxid("hb_pv_score2")
    private val hbCountdown2: HBox by fxid("hb_pv_countdown2")

    init {
        with(gameController.player1) {
            bindName(lName1, this)
            bindScore(hbScore1, this)
            bindCountdown(hbCountdown1, this)
        }

        with(gameController.player2) {
            bindName(lName2, this)
            bindScore(hbScore2, this)
            bindCountdown(hbCountdown2, this, true)
        }
    }

    private fun bindName(view: Label, player: PlayerModel) = with(view) {
        textProperty().bind(player.name)
        val buzzered = object : BooleanPropertyBase(false) {
            override fun getName(): String = "buzzered"
            override fun getBean(): Any = view
            override fun invalidated() = pseudoClassStateChanged(BUZZERED_PSEUDO_CLASS, get())
        }

        player.hasBuzzered.addListener { _, _, hasBuzzered -> buzzered.set(hasBuzzered) }
    }

    private fun bindScore(view: HBox, player: PlayerModel) = with(view) {
        player.score.addListener { _, _, newValue ->
            children.clear()
            for (i in 1..newValue.toInt()) {
                val checkmark = MaterialDesignIconFactory.get()
                        .createIcon(MaterialDesignIcon.CHECK, "64pt")
                        .apply {
                            style += "-fx-fill: green;"
                            JFXDepthManager.setDepth(this, 1)
                        }
                JFXDepthManager.setDepth(checkmark, 1)
                children.add(checkmark)
            }
        }
    }

    private fun bindCountdown(view: HBox, player: PlayerModel, reverseOrder: Boolean = false) = with(view) {
        visibleWhen(player.hasBuzzered)
        player.countdown.addListener { _, _, newValue ->
            children.clear()
            val count = gameController.countdownStart - newValue
            val circles = mutableListOf<Node>()
            for (i in 0 until count) {
                val color = when {
                    count < gameController.countdownStart -> "green"
                    else -> "-color-secondary-variant"
                }
                val circle = MaterialDesignIconFactory.get()
                        .createIcon(MaterialDesignIcon.CIRCLE, "64pt")
                        .apply {
                            style += "-fx-fill: $color;"
                            JFXDepthManager.setDepth(this, 1)
                        }
                circles.add(circle)
            }
            circles.takeIf { reverseOrder }?.reverse()
            children.addAll(circles)
        }
    }
    //</editor-fold>

    private companion object {
        @JvmStatic
        val BUZZERED_PSEUDO_CLASS = PseudoClass.getPseudoClass("buzzered")
    }

}
