package net.myacxy.bpgg.models

sealed class GameEvent {
    class Unblur(val pathToPicture: String?) : GameEvent()
    class Buzzer(val player: PlayerModel) : GameEvent()
    class Score(val player: PlayerModel) : GameEvent()
    object Reveal : GameEvent()
}
