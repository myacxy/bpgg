package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.util.converter.IntegerStringConverter
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
    private val btnBuzzer1: JFXButton by fxid("btn_gmv_buzzer1")

    init {
        with(gameController.player1) {
            tfName1.textProperty().bindBidirectional(name)
            tfScore1.textProperty().bindBidirectional(score, IntegerStringConverter())
            btnScoreUp1.action { gameController.onGameEvent(GameEvent.ScoreUp(item)) }
            btnScoreDown1.action { gameController.onGameEvent(GameEvent.ScoreDown(item)) }
            btnBuzzer1.apply {
                disableWhen { gameController.canPlayerBuzzerProperty.not() }
                action { gameController.onGameEvent(GameEvent.Buzzer(item)) }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="player2">
    private val tfName2: JFXTextField by fxid("tf_gmv_name2")
    private val tfScore2: JFXTextField by fxid("tf_gmv_score2")
    private val btnScoreUp2: JFXButton by fxid("btn_gmv_score_up2")
    private val btnScoreDown2: JFXButton by fxid("btn_gmv_score_down2")
    private val btnBuzzer2: JFXButton by fxid("btn_gmv_buzzer2")

    init {
        with(gameController.player2) {
            tfName2.textProperty().bindBidirectional(name)
            tfScore2.textProperty().bindBidirectional(score, IntegerStringConverter())
            btnScoreUp2.action { gameController.onGameEvent(GameEvent.ScoreUp(item)) }
            btnScoreDown2.action { gameController.onGameEvent(GameEvent.ScoreDown(item)) }
            btnBuzzer2.apply {
                disableWhen { gameController.canPlayerBuzzerProperty.not() }
                action { gameController.onGameEvent(GameEvent.Buzzer(item)) }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="settings">
    private val vbSettings: VBox by fxid("vb_gmv_settings")
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
                    .or(gameController.shouldRevealProperty)
            disableProperty().bind(disable)
            action { gameController.onGameEvent(GameEvent.Start) }
        }
        btnPause.apply {
            val disable = gameController.hasPictureProperty.not()
                    .or(gameController.isInProgressProperty.not())
                    .or(gameController.shouldRevealProperty)
            disableProperty().bind(disable)
            action { gameController.onGameEvent(GameEvent.Pause) }
        }
        btnReveal.apply {
            val disable = gameController.hasPictureProperty.not()
                    .or(gameController.shouldRevealProperty)
            disableProperty().bind(disable)
            action { gameController.onGameEvent(GameEvent.Reveal) }
        }
    }
    //</editor-fold>

}
