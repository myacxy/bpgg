package net.myacxy.bpgg.models

import tornadofx.*

class Player(name: String,
             score: Int = 0,
             hasBuzzered: Boolean = false,
             countdown: Int = 0) {

    var name by property(name)
    fun nameProperty() = getProperty(Player::name)

    var score by property(score)
    fun scoreProperty() = getProperty(Player::score)

    var hasBuzzered by property(hasBuzzered)
    fun hasBuzzeredProperty() = getProperty(Player::hasBuzzered)

    var countdown by property(countdown)
    fun countdownProperty() = getProperty(Player::countdown)

}
