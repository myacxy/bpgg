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

    private val background: Clip = AudioSystem.getClip()
    private val beep: AudioClip
    private val boop: AudioClip
    private val intro: Clip = AudioSystem.getClip()

    init {
        background.apply {
            val ais = BufferedInputStream(resources.stream("/sounds/background.wav"))
            open(AudioSystem.getAudioInputStream(ais))
            addLineListener { isBackgroundMusicPlaying = it.type == LineEvent.Type.START }
            val gainControl = getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            gainControl.value = -15f
        }
        beep = AudioClip(resources["/sounds/beep.wav"])
        boop = AudioClip(resources["/sounds/boop.wav"])
        intro.apply {
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

    fun playBeep() {
        beep.play()
    }

    fun playBoop() {
        boop.play()
    }

}
