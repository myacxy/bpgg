package net.myacxy.bpgg.models

sealed class GameEvent {
    class Buzzer(val player: Player) : GameEvent()
    class NewPicture(val pathToPicture: String?) : GameEvent()
    class ScoreDown(val player: Player) : GameEvent()
    class ScoreUp(val player: Player) : GameEvent()
    object Pause : GameEvent()
    object Reveal : GameEvent()
    object Start : GameEvent()
}
