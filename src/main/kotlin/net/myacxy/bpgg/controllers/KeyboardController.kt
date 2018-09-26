package net.myacxy.bpgg.controllers

import net.myacxy.bpgg.models.KeyboardListener
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.SwingKeyAdapter
import tornadofx.*
import java.awt.event.KeyEvent
import java.util.logging.Level
import java.util.logging.Logger

class KeyboardController : Controller() {

    private val nativeKeyListener = NativeKeyListener()

    fun onStart() {
        Logger.getLogger(GlobalScreen::class.java.`package`.name).apply {
            level = Level.OFF
            useParentHandlers = false
        }
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(nativeKeyListener)
    }

    fun onStop() {
        GlobalScreen.removeNativeKeyListener(nativeKeyListener)
        GlobalScreen.unregisterNativeHook()
    }

    fun setKeyboardListener(keyboardListener: KeyboardListener?) {
        nativeKeyListener.keyboardListener = keyboardListener
    }

    private class NativeKeyListener : SwingKeyAdapter() {

        var keyboardListener: KeyboardListener? = null

        override fun keyPressed(event: KeyEvent) {
            keyboardListener?.let { runLater { it.onKeyPressed(event) } }
        }

        override fun getJavaKeyEvent(nativeEvent: NativeKeyEvent): KeyEvent {
            val originalEvent = super.getJavaKeyEvent(nativeEvent)
            val newKeyLocation = when (nativeEvent.keyLocation) {
                NativeKeyEvent.KEY_LOCATION_LEFT -> KeyEvent.KEY_LOCATION_LEFT
                else -> originalEvent.keyLocation
            }
            return KeyEvent(this,
                    nativeEvent.id - (NativeKeyEvent.NATIVE_KEY_FIRST - KeyEvent.KEY_FIRST),
                    originalEvent.`when`,
                    originalEvent.modifiers,
                    originalEvent.keyCode,
                    originalEvent.keyChar,
                    newKeyLocation)
        }
    }

}
