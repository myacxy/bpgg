package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.util.converter.IntegerStringConverter
import javafx.util.converter.NumberStringConverter
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.SettingsController
import net.myacxy.bpgg.models.GameEvent
import tornadofx.*

class GameMasterView : View() {

    override val root: BorderPane by fxml("/GameMasterView.fxml")

    private val settingsController: SettingsController by inject()
    private val gameController: GameController by inject()

    //<editor-fold desc="player1">
    private val tfName1: JFXTextField by fxid("tf_gmv_name1")
    private val tfScore1: JFXTextField by fxid("tf_gmv_score1")
    private val btnScoreUp1: JFXButton by fxid("btn_gmv_score_up1")
    private val btnScoreDown1: JFXButton by fxid("btn_gmv_score_down1")

    init {
        tfName1.textProperty().bindBidirectional(gameController.player1.name)
        tfScore1.textProperty().bindBidirectional(gameController.player1.score, IntegerStringConverter())
        btnScoreUp1.action { gameController.onGameEvent(GameEvent.ScoreUp(gameController.player1.item)) }
        btnScoreDown1.action { gameController.onGameEvent(GameEvent.ScoreDown(gameController.player1.item)) }

    }
    //</editor-fold>

    //<editor-fold desc="player2">
    private val tfName2: JFXTextField by fxid("tf_gmv_name2")
    private val tfScore2: JFXTextField by fxid("tf_gmv_score2")
    private val btnScoreUp2: JFXButton by fxid("btn_gmv_score_up2")
    private val btnScoreDown2: JFXButton by fxid("btn_gmv_score_down2")

    init {
        tfName2.textProperty().bindBidirectional(gameController.player2.name)
        tfScore2.textProperty().bindBidirectional(gameController.player2.score, IntegerStringConverter())
        btnScoreUp2.action { gameController.onGameEvent(GameEvent.ScoreUp(gameController.player2.item)) }
        btnScoreDown2.action { gameController.onGameEvent(GameEvent.ScoreDown(gameController.player2.item)) }
    }
    //</editor-fold>

    //<editor-fold desc="settings">
    private val vbSettings: VBox by fxid("vb_gmv_settings")

    private val tfUnblurDuration: JFXTextField by fxid("tf_gmv_unblur_duration")
    private val btnUnblurDurationUp: JFXButton by fxid("btn_gmv_unblur_duration_up")
    private val btnUnblurDuration: JFXButton by fxid("btn_gmv_unblur_duration_down")

    private val tfMinimumBlur: JFXTextField by fxid("tf_gmv_min_blur")
    private val btnMinimumBlurUp: JFXButton by fxid("btn_gmv_min_blur_up")
    private val btnMinimumBlurDown: JFXButton by fxid("btn_gmv_min_blur_down")

    private val tfMaximumBlur: JFXTextField by fxid("tf_gmv_max_blur")
    private val btnMaximumBlurUp: JFXButton by fxid("btn_gmv_max_blur_up")
    private val btnMaximumBlurDown: JFXButton by fxid("btn_gmv_max_blur_down")

    init {
        vbSettings.disableProperty().bind(gameController.isInProgressProperty)

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
    //</editor-fold>

    //<editor-fold desc="actions">
    private val btnChoosePicture: JFXButton by fxid("btn_gmv_choose_picture")
    private val btnStart: JFXButton by fxid("btn_gmv_start")
    private val btnPause: JFXButton by fxid("btn_gmv_pause")
    private val btnReveal: JFXButton by fxid("btn_gmv_reveal")

    init {
        btnChoosePicture.action {
            val pathToFile = settingsController.choosePicture()
            gameController.onGameEvent(GameEvent.NewPicture(pathToFile))
        }
        btnStart.apply {
            val disable = gameController.hasPictureProperty.not()
                    .or(gameController.isInProgressProperty)
            disableProperty().bind(disable)
            action { gameController.onGameEvent(GameEvent.Start) }
        }
        btnPause.apply {
            val disable = gameController.hasPictureProperty.not()
                    .or(gameController.isInProgressProperty.not())
            disableProperty().bind(disable)
            action { gameController.onGameEvent(GameEvent.Pause) }
        }
        btnReveal.apply {
            val disable = gameController.hasPictureProperty.not()
            disableProperty().bind(disable)
            action { gameController.onGameEvent(GameEvent.Reveal) }
        }
    }
    //</editor-fold>

}
