package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.scene.layout.BorderPane
import javafx.util.converter.IntegerStringConverter
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.SettingsController
import net.myacxy.bpgg.models.GameEvent
import tornadofx.*

class GameMasterView : View() {

    override val root: BorderPane by fxml("/GameMasterView.fxml")

    private val settingsController: SettingsController by inject()
    private val gameController: GameController by inject()
    private val iconFactory = MaterialDesignIconFactory.get()

    //<editor-fold desc="player1">
    private val tfName1: JFXTextField by fxid("name1")
    private val tfScore1: JFXTextField by fxid("score1")
    private val btnScore1Up: JFXButton by fxid("score1_up")
    private val btnScore1Down: JFXButton by fxid("score1_down")
    private val btnBuzzer1: JFXButton by fxid("buzzer1")

    init {
        with(gameController.player1) {
            tfName1.textProperty().bindBidirectional(name)
            tfScore1.textProperty().bindBidirectional(score, IntegerStringConverter())
            btnScore1Up.apply {
                graphic = iconFactory.createIcon(MaterialDesignIcon.PLUS, "24")
                action { gameController.onGameEvent(GameEvent.ScoreUp(item)) }
            }
            btnScore1Down.apply {
                graphic = iconFactory.createIcon(MaterialDesignIcon.MINUS, "24")
                action { gameController.onGameEvent(GameEvent.ScoreDown(item)) }
            }
            btnBuzzer1.apply {
                disableWhen { gameController.canPlayerBuzzerProperty.not() }
                action { gameController.onGameEvent(GameEvent.Buzzer(item)) }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="player2">
    private val tfName2: JFXTextField by fxid("name2")
    private val tfScore2: JFXTextField by fxid("score2")
    private val btnScore2Up: JFXButton by fxid("score2_up")
    private val btnScore2Down: JFXButton by fxid("score2_down")
    private val btnBuzzer2: JFXButton by fxid("buzzer2")

    init {
        with(gameController.player2) {
            tfName2.textProperty().bindBidirectional(name)
            tfScore2.textProperty().bindBidirectional(score, IntegerStringConverter())
            btnScore2Up.apply {
                graphic = iconFactory.createIcon(MaterialDesignIcon.PLUS, "24")
                action { gameController.onGameEvent(GameEvent.ScoreUp(item)) }
            }
            btnScore2Down.apply {
                graphic = iconFactory.createIcon(MaterialDesignIcon.MINUS, "24")
                action { gameController.onGameEvent(GameEvent.ScoreDown(item)) }
            }
            btnBuzzer2.apply {
                disableWhen { gameController.canPlayerBuzzerProperty.not() }
                action { gameController.onGameEvent(GameEvent.Buzzer(item)) }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="actions">
    private val btnChoosePicture: JFXButton by fxid("choose_picture")
    private val btnStart: JFXButton by fxid("start")
    private val btnPause: JFXButton by fxid("pause")
    private val btnReveal: JFXButton by fxid("reveal")

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
