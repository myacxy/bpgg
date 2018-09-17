package net.myacxy.bpgg.models

sealed class GameEvent {
    class Unblur(val pathToPicture: String?) : GameEvent()
    class Buzzer(val player: Player) : GameEvent()
    class ScoreDown(val player: Player) : GameEvent()
    class ScoreUp(val player: Player) : GameEvent()
    object Reveal : GameEvent()
}
