package net.myacxy.bpgg.controllers

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import net.myacxy.bpgg.models.GameEvent
import net.myacxy.bpgg.models.Player
import net.myacxy.bpgg.models.PlayerModel
import tornadofx.*
import java.util.concurrent.TimeUnit

class GameController : Controller() {

    val player1 = PlayerModel()
    val player2 = PlayerModel()

    val currentPathOfGuessablePictureProperty = SimpleStringProperty()
    var currentPathOfGuessablePicture: String? by currentPathOfGuessablePictureProperty

    val currentBlurProperty = SimpleDoubleProperty(MAXIMUM_BLUR)
    var currentBlur: Double by currentBlurProperty

    private var animationDisposable: Disposable? = null

    init {
        player1.item = Player(messages["title_player1"])
        player2.item = Player(messages["title_player2"])
    }

    fun onGameEvent(event: GameEvent) = when (event) {
        is GameEvent.Unblur -> onUnblurEvent(event.pathToPicture)
        is GameEvent.Buzzer -> onBuzzerEvent(event.player)
        is GameEvent.ScoreDown -> onScoreDownEvent(event.player)
        is GameEvent.ScoreUp -> onScoreUpEvent(event.player)
        GameEvent.Reveal -> onRevealEvent()
    }

    private fun onUnblurEvent(path: String?) {

        fun resetPicture() {
            animationDisposable?.dispose()
            currentBlur = MAXIMUM_BLUR
            currentPathOfGuessablePicture = path
        }

        fun animateBlurryPicture() {
            if (path.isNullOrEmpty()) return

            animationDisposable = Observable.interval(INTERVAL_MILLISECONDS_UNBLUR, TimeUnit.MILLISECONDS)
                    .map { it * INTERVAL_MILLISECONDS_UNBLUR }
                    .takeWhile { it < DURATION_MILLISECONDS_UNBLUR }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribeBy(onError = {
                        it.printStackTrace()
                    }, onNext = {
                        currentBlur = MINIMUM_BLUR + ((MAXIMUM_BLUR - MINIMUM_BLUR) * ((DURATION_MILLISECONDS_UNBLUR - it) / DURATION_MILLISECONDS_UNBLUR.toDouble()))
                    })
        }

        resetPicture()
        animateBlurryPicture()
    }

    private fun onBuzzerEvent(player: Player) {
    }

    private fun onScoreDownEvent(player: Player) {
        player.score -= 1
    }

    private fun onScoreUpEvent(player: Player) {
        player.score += 1
    }

    private fun onRevealEvent() {
        if (currentPathOfGuessablePicture.isNullOrEmpty()) return

        animationDisposable?.dispose()
        val startBlur = currentBlur

        animationDisposable = Observable.interval(INTERVAL_MILLISECONDS_UNBLUR, TimeUnit.MILLISECONDS)
                .map { it * INTERVAL_MILLISECONDS_UNBLUR }
                .takeWhile { it < DURATION_MILLISECONDS_REVEAL }
                .subscribeOn(Schedulers.computation())
                .observeOn(JavaFxScheduler.platform())
                .subscribeBy(onError = {
                    it.printStackTrace()
                }, onNext = {
                    currentBlur = startBlur * ((DURATION_MILLISECONDS_REVEAL - it) / DURATION_MILLISECONDS_REVEAL.toDouble())
                })
    }

    private companion object {
        const val MAXIMUM_SCORE = 5
        const val MAXIMUM_BLUR = 100.0
        const val MINIMUM_BLUR = 20.0
        const val INTERVAL_MILLISECONDS_UNBLUR = 17L
        const val DURATION_MILLISECONDS_REVEAL = 300L
        const val DURATION_MILLISECONDS_UNBLUR = 10000L
    }

}
