package net.myacxy.bpgg.controllers

import com.ivan.xinput.XInputDevice14
import com.ivan.xinput.enums.XInputButton
import com.ivan.xinput.listener.SimpleXInputDeviceListener
import io.reactivex.Observable
import io.reactivex.Observable.intervalRange
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.plusAssign
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
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import tornadofx.*
import java.util.concurrent.TimeUnit

class GameController : Controller(), NativeKeyListener {

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
    private val controllerDisposables = CompositeDisposable()

    init {
        player1.item = Player(messages["title_player1"])
        player2.item = Player(messages["title_player2"])

        GlobalScreen.addNativeKeyListener(this)
        initializeGamepadsAsBuzzers()

    }

    private fun initializeGamepadsAsBuzzers() {

        fun initializeGamepadAsBuzzer(device: XInputDevice14, player: Player) {

            device.addListener(object : SimpleXInputDeviceListener() {
                override fun buttonChanged(button: XInputButton, pressed: Boolean) {
                    onBuzzerEvent(player)
                }
            })

            controllerDisposables += Observable.interval(0, 17L, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doOnNext { device.poll() }
                    .subscribeBy(onError = { it.printStackTrace() })
        }

        val devices = XInputDevice14.getAllDevices().filter { it.gamepadCapabilities != null }
        devices.getOrNull(0)?.run { initializeGamepadAsBuzzer(this, player1.item) }
        devices.getOrNull(1)?.run { initializeGamepadAsBuzzer(this, player2.item) }
    }

    override fun nativeKeyTyped(p0: NativeKeyEvent) = Unit

    override fun nativeKeyPressed(event: NativeKeyEvent) = when (event.keyCode to event.keyLocation) {
        NativeKeyEvent.VC_CONTROL to NativeKeyEvent.KEY_LOCATION_LEFT -> runLater { onBuzzerEvent(player1.item) }
        NativeKeyEvent.VC_CONTROL to NativeKeyEvent.KEY_LOCATION_RIGHT -> runLater { onBuzzerEvent(player2.item) }
        else -> Unit
    }

    override fun nativeKeyReleased(p0: NativeKeyEvent) = Unit

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

        progressDisposable = intervalRange(start, end - start, 0, 17L, TimeUnit.MILLISECONDS)
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
        if (!canPlayerBuzzerProperty.value) return

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

}
