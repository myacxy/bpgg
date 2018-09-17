package net.myacxy.bpgg.models

import tornadofx.*

class Player(name: String, score: Int = 0) {

    var name by property(name)
    fun nameProperty() = getProperty(Player::name)

    var score by property(score)
    fun scoreProperty() = getProperty(Player::score)

}
