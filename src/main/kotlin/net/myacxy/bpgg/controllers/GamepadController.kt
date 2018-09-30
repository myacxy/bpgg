package net.myacxy.bpgg.controllers

import com.ivan.xinput.XInputDevice14
import com.ivan.xinput.enums.XInputButton
import com.ivan.xinput.listener.SimpleXInputDeviceListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.myacxy.bpgg.models.GamepadListener
import tornadofx.*
import java.util.concurrent.TimeUnit

class GamepadController : Controller() {

    var gamepadListener: GamepadListener? = null

    private val gamepadDisposables = CompositeDisposable()

    fun initialize() {
        gamepadDisposables.dispose()
        setUpGamepadPolling()
    }

    private fun setUpGamepadPolling() {

        fun pollGamepad(device: XInputDevice14, index: Int) {

            val listener = object : SimpleXInputDeviceListener() {
                override fun buttonChanged(button: XInputButton, pressed: Boolean) {
                    gamepadListener?.onAnyButtonPressed(index)
                }
            }

            gamepadDisposables += Observable.interval(0, 17L, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doOnSubscribe { device.addListener(listener) }
                    .doOnNext { device.poll() }
                    .doFinally { device.removeListener(listener) }
                    .subscribeBy(onError = { it.printStackTrace() })
        }

        XInputDevice14.getAllDevices()
                .filter { it.gamepadCapabilities != null }
                .forEachIndexed { index, gamepad -> pollGamepad(gamepad, index) }
    }

}
