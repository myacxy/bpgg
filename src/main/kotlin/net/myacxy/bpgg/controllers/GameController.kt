package net.myacxy.bpgg.controllers

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javafx.animation.Interpolator
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import net.myacxy.bpgg.models.GameEvent
import net.myacxy.bpgg.models.Player
import net.myacxy.bpgg.models.PlayerModel
import tornadofx.*
import java.util.concurrent.TimeUnit

class GameController : Controller() {

    val player1 = PlayerModel()
    val player2 = PlayerModel()

    val pictureProperty = SimpleStringProperty()
    var picture: String? by pictureProperty

    val hasPictureProperty = pictureProperty.booleanBinding { picture.isNullOrEmpty().not() }

    val timerProperty = SimpleLongProperty(10L)
    var timer: Long by timerProperty

    val progressProperty = SimpleDoubleProperty(0.0)
    var progress: Double by progressProperty

    val isInProgressProperty = SimpleBooleanProperty(false)
    var isInProgress: Boolean by isInProgressProperty

    val shouldRevealProperty = SimpleBooleanProperty(false)
    var shouldReveal by shouldRevealProperty

    val canPlayerBuzzerProperty = hasPictureProperty
            .and(isInProgressProperty)
            .and(player1.hasBuzzered.booleanBinding { it == false })
            .and(player2.hasBuzzered.booleanBinding { it == false })

    val countdownStart = 5

    private var progressDisposable: Disposable? = null

    init {
        player1.item = Player(messages["title_player1"])
        player2.item = Player(messages["title_player2"])
    }

    fun onGameEvent(event: GameEvent) = when (event) {
        is GameEvent.Buzzer -> onBuzzerEvent(event.player)
        is GameEvent.NewPicture -> onNewPictureEvent(event.pathToPicture)
        GameEvent.Pause -> onPauseEvent()
        GameEvent.Reveal -> onRevealEvent()
        is GameEvent.ScoreDown -> onScoreDownEvent(event.player)
        is GameEvent.ScoreUp -> onScoreUpEvent(event.player)
        GameEvent.Start -> onStartEvent()
    }

    private fun onNewPictureEvent(path: String?) {
        progressDisposable?.dispose()
        progress = 0.0
        shouldReveal = false
        picture = path
    }

    private fun onStartEvent() {
        progressDisposable?.dispose()
        player1.item.hasBuzzered = false
        player2.item.hasBuzzered = false

        val end = TimeUnit.SECONDS.toMillis(timer).div(17L)
        val start = (end * progress.div(100.0)).toLong()

        progressDisposable = Observable.intervalRange(start, end - start, 0, 17L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(JavaFxScheduler.platform())
                .doOnSubscribe { isInProgress = true }
                .doFinally { isInProgress = false }
                .subscribeBy(onNext = { progress = it.times(100.0).div(end) }, onError = { it.printStackTrace() })
    }

    private fun onPauseEvent() {
        progressDisposable?.dispose()
    }

    private fun onBuzzerEvent(player: Player) {
        progressDisposable?.dispose()
        player.hasBuzzered = true
        player.countdown = 5
        player.countdownProperty().animate(0, 5.seconds, Interpolator.LINEAR)
    }

    private fun onScoreDownEvent(player: Player) {
        player.score -= 1
    }

    private fun onScoreUpEvent(player: Player) {
        player.score += 1
    }

    private fun onRevealEvent() {
        progressDisposable?.dispose()
        runLater {
            progress = 100.0
            shouldReveal = true
        }
    }

    private companion object {
        const val MAXIMUM_SCORE = 7
        const val DURATION_BUZZER = 5000L
    }

}
