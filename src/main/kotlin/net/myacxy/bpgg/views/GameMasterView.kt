package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import javafx.scene.layout.BorderPane
import javafx.util.converter.IntegerStringConverter
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.models.GameEvent
import tornadofx.*

class GameMasterView : View() {

    override val root: BorderPane by fxml("/GameMasterView.fxml")

    private val gameController: GameController by inject()

    private val tfName1: JFXTextField by fxid("tf_gmv_name1")
    private val tfScore1: JFXTextField by fxid("tf_gmv_score1")
    private val btnScoreUp1: JFXButton by fxid("btn_gmv_score_up1")
    private val btnScoreDown1: JFXButton by fxid("btn_gmv_score_down1")

    init {
        tfName1.apply {
            textProperty().bindBidirectional(gameController.player1.name)
        }
        tfScore1.apply {
            textProperty().bindBidirectional(gameController.player1.score, IntegerStringConverter())
        }
        btnScoreUp1.apply {
            action { gameController.onGameEvent(GameEvent.ScoreUp(gameController.player1.item)) }
        }
        btnScoreDown1.apply {
            action { gameController.onGameEvent(GameEvent.ScoreDown(gameController.player1.item)) }
        }
    }

}
