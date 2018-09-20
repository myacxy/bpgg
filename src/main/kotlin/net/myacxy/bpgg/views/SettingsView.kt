package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import javafx.scene.layout.VBox
import javafx.util.converter.NumberStringConverter
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.SettingsController
import tornadofx.*

class SettingsView : View() {

    override val root: VBox by fxml("/SettingsView.fxml")

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

    init {
        root.disableProperty().bind(gameController.isInProgressProperty)

        tfUnblurDuration.textProperty().bindBidirectional(gameController.timerProperty, NumberStringConverter())
        btnUnblurDurationUp.action { gameController.timer += 1 }
        btnUnblurDuration.action { gameController.timer -= 1 }

        tfMinimumBlur.textProperty().bindBidirectional(settingsController.minimumBlurProperty, NumberStringConverter())
        btnMinimumBlurUp.action { settingsController.minimumBlur += 1 }
        btnMinimumBlurDown.action { settingsController.minimumBlur -= 1 }

        tfMaximumBlur.textProperty().bindBidirectional(settingsController.maximumBlurProperty, NumberStringConverter())
        btnMaximumBlurUp.action { settingsController.maximumBlur += 1 }
        btnMaximumBlurDown.action { settingsController.maximumBlur -= 1 }
    }

}
