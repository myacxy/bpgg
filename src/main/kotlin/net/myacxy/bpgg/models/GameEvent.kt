package net.myacxy.bpgg.models

sealed class GameEvent {
    class Buzzer(val player: Player) : GameEvent()
    class NewPictures(val filePaths: List<String>) : GameEvent()
    object Pause : GameEvent()
    class PictureSelect(val filePath: String?) : GameEvent()
    object Reveal : GameEvent()
    class ScoreDown(val player: Player) : GameEvent()
    class ScoreUp(val player: Player) : GameEvent()
    object Start : GameEvent()
}
