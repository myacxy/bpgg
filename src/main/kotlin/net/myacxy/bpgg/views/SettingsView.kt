package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.scene.layout.VBox
import javafx.util.converter.NumberStringConverter
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.MainController
import net.myacxy.bpgg.controllers.SettingsController
import tornadofx.*

class SettingsView : View() {

    override val root: VBox by fxml("/SettingsView.fxml")

    private val mainController: MainController by inject()
    private val settingsController: SettingsController by inject()
    private val gameController: GameController by inject()

    private val tfUnblurDuration: JFXTextField by fxid("unblur_duration")
    private val btnUnblurDurationUp: JFXButton by fxid("unblur_duration_up")
    private val btnUnblurDuration: JFXButton by fxid("unblur_duration_down")

    private val tfMinimumBlur: JFXTextField by fxid("min_blur")
    private val btnMinimumBlurUp: JFXButton by fxid("min_blur_up")
    private val btnMinimumBlurDown: JFXButton by fxid("min_blur_down")

    private val tfMaximumBlur: JFXTextField by fxid("max_blur")
    private val btnMaximumBlurUp: JFXButton by fxid("max_blur_up")
    private val btnMaximumBlurDown: JFXButton by fxid("max_blur_down")

    private val settingsPlayer1: PlayerSettingsView by inject(mainController.player1Scope)
    private val settingsPlayer2: PlayerSettingsView by inject(mainController.player2Scope)

    private val iconFactory = MaterialDesignIconFactory.get()

    init {
        root.disableProperty().bind(gameController.isInProgressProperty)

        tfUnblurDuration.textProperty().bindBidirectional(gameController.timerProperty, NumberStringConverter())
        btnUnblurDurationUp.apply {
            graphic = iconFactory.createIcon(MaterialDesignIcon.PLUS, "24")
            action { gameController.timer += 1 }
        }
        btnUnblurDuration.apply {
            graphic = iconFactory.createIcon(MaterialDesignIcon.MINUS, "24")
            action { gameController.timer -= 1 }
        }

        tfMinimumBlur.textProperty().bindBidirectional(settingsController.minimumBlurProperty, NumberStringConverter())
        btnMinimumBlurUp.apply {
            graphic = iconFactory.createIcon(MaterialDesignIcon.PLUS, "24")
            action { settingsController.minimumBlur += 1 }
        }
        btnMinimumBlurDown.apply {
            graphic = iconFactory.createIcon(MaterialDesignIcon.MINUS, "24")
            action { settingsController.minimumBlur -= 1 }
        }

        tfMaximumBlur.textProperty().bindBidirectional(settingsController.maximumBlurProperty, NumberStringConverter())
        btnMaximumBlurUp.apply {
            graphic = iconFactory.createIcon(MaterialDesignIcon.PLUS, "24")
            action { settingsController.maximumBlur += 1 }
        }
        btnMaximumBlurDown.apply {
            graphic = iconFactory.createIcon(MaterialDesignIcon.MINUS, "24")
            action { settingsController.maximumBlur -= 1 }
        }

        root += settingsPlayer1
        root += settingsPlayer2
    }

}
