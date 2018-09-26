package net.myacxy.bpgg.controllers

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.media.AudioClip
import tornadofx.*
import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl
import javax.sound.sampled.LineEvent

class SoundController : Controller() {

    val isBackgroundMusicPlayingProperty = SimpleBooleanProperty(false)
    var isBackgroundMusicPlaying by isBackgroundMusicPlayingProperty

    val isIntroMusicPlayingProperty = SimpleBooleanProperty(false)
    var isIntroMusicPlaying by isIntroMusicPlayingProperty

    private val background by lazy {
        return@lazy AudioSystem.getClip().apply {
            val ais = BufferedInputStream(resources.stream("/sounds/background.wav"))
            open(AudioSystem.getAudioInputStream(ais))
            addLineListener { isBackgroundMusicPlaying = it.type == LineEvent.Type.START }
            val gainControl = getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            gainControl.value = -15f
        }
    }
    private val beep by lazy { AudioClip(resources["/sounds/beep.wav"]) }
    private val boop by lazy { AudioClip(resources["/sounds/boop.wav"]) }
    private val intro by lazy {
        return@lazy AudioSystem.getClip().apply {
            val ais = BufferedInputStream(resources.stream("/sounds/intro.wav"))
            open(AudioSystem.getAudioInputStream(ais))
            addLineListener { isIntroMusicPlaying = it.type == LineEvent.Type.START }
        }
    }

    fun toggleBackgroundMusic() = when {
        background.isActive -> background.stop()
        else -> background.loop(Clip.LOOP_CONTINUOUSLY)
    }

    fun toggleIntroMusic() = when {
        intro.isActive -> intro.stop()
        else -> intro.apply { framePosition = 0 }.run { start() }
    }

    fun playCountdown(current: Int) = when (current) {
        0 -> boop.play()
        else -> beep.play()
    }

}
