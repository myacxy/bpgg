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
import javafx.beans.binding.BooleanBinding
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import net.myacxy.bpgg.models.GameEvent
import net.myacxy.bpgg.models.KeyboardListener
import net.myacxy.bpgg.models.Player
import net.myacxy.bpgg.models.PlayerModel
import tornadofx.*
import java.awt.event.KeyEvent
import java.util.concurrent.TimeUnit

class GameController : Controller() {

    val player1 = PlayerModel()
    val player2 = PlayerModel()

    val picturesProperty = SimpleListProperty<String>(FXCollections.observableArrayList())
    var pictures: ObservableList<String> by picturesProperty

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

    val canPlayerBuzzerProperty: BooleanBinding = hasPictureProperty
            .and(isInProgressProperty)
            .and(player1.hasBuzzered.booleanBinding { it == false })
            .and(player2.hasBuzzered.booleanBinding { it == false })

    val showPictureProperty: BooleanBinding = canPlayerBuzzerProperty.or(shouldRevealProperty)

    val countdownStart = 5

    private val keyboardController: KeyboardController by inject()
    private val soundController: SoundController by inject()

    private var progressDisposable: Disposable? = null
    private var countdownDisposable: Disposable? = null
    private val controllerDisposables = CompositeDisposable()

    init {
        player1.item = Player(messages["title_player1"])
        player2.item = Player(messages["title_player2"])

        initializeKeyboardAsBuzzers()
        initializeGamepadsAsBuzzers()
    }

    private fun initializeKeyboardAsBuzzers() {
        keyboardController.setKeyboardListener(object : KeyboardListener {
            override fun onKeyPressed(event: KeyEvent) {
                val player = when (event.keyCode to event.keyLocation) {
                    KeyEvent.VK_CONTROL to KeyEvent.KEY_LOCATION_LEFT -> player1.item
                    KeyEvent.VK_CONTROL to KeyEvent.KEY_LOCATION_RIGHT -> player2.item
                    else -> null
                }
                player?.run { onGameEvent(GameEvent.Buzzer(this)) }
            }
        })
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

    fun onGameEvent(event: GameEvent) = when (event) {
        is GameEvent.Buzzer -> onBuzzerEvent(event.player)
        is GameEvent.NewPictures -> onNewPicturesEvent(event.filePaths)
        GameEvent.Pause -> onPauseEvent()
        is GameEvent.PictureSelect -> onNewPictureEvent(event.filePath)
        GameEvent.Reveal -> onRevealEvent()
        is GameEvent.ScoreDown -> onScoreDownEvent(event.player)
        is GameEvent.ScoreUp -> onScoreUpEvent(event.player)
        GameEvent.Start -> onStartEvent()
    }

    private fun onNewPictureEvent(filePath: String?) {
        progressDisposable?.dispose()
        countdownDisposable?.dispose()

        picture = filePath
        progress = 0.0
        shouldReveal = false
        player1.item.hasBuzzered = false
        player2.item.hasBuzzered = false
    }

    private fun onNewPicturesEvent(filePaths: List<String>) {
        onNewPictureEvent(null)
        pictures.setAll(filePaths)
    }

    private fun onStartEvent() {
        progressDisposable?.dispose()
        countdownDisposable?.dispose()

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

        countdownDisposable?.dispose()
        progressDisposable?.dispose()

        player.hasBuzzered = true
        player.countdown = countdownStart

        countdownDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .takeWhile { player.countdown > 0 }
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .subscribeBy(onNext = {
                    player.countdown -= 1
                    soundController.playCountdown(player.countdown)
                }, onError = {
                    it.printStackTrace()
                })
    }

    private fun onScoreDownEvent(player: Player) {
        player.score -= 1
    }

    private fun onScoreUpEvent(player: Player) {
        player.score += 1
    }

    private fun onRevealEvent() {
        progressDisposable?.dispose()
        countdownDisposable?.dispose()

        runLater {
            progress = 100.0
            shouldReveal = true
        }
    }

}
