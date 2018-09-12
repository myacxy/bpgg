package net.myacxy.bpgg.controllers

import javafx.beans.property.SimpleStringProperty
import net.myacxy.bpgg.models.Player
import net.myacxy.bpgg.models.PlayerModel
import tornadofx.Controller
import tornadofx.get
import tornadofx.getValue
import tornadofx.setValue

class GameController : Controller() {

    val player1 = PlayerModel()
    val player2 = PlayerModel()

    val currentPathOfGuessablePictureProperty = SimpleStringProperty()
    var currentPathOfGuessablePicture: String? by currentPathOfGuessablePictureProperty

    init {
        player1.item = Player(messages["descr_player1"])
        player2.item = Player(messages["descr_player2"])
    }

    private companion object {
        const val MAXIMUM_SCORE = 5
    }
}
