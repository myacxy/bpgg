package net.myacxy.bpgg.controllers

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.media.AudioClip
import tornadofx.*
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class SoundController : Controller() {

    val isBackgroundMusicPlayingProperty = SimpleBooleanProperty(false)
    var isBackgroundMusicPlaying by isBackgroundMusicPlayingProperty

    val isIntroMusicPlayingProperty = SimpleBooleanProperty(false)
    var isIntroMusicPlaying by isIntroMusicPlayingProperty

    private val background: Clip = AudioSystem.getClip()
    private val intro: Clip = AudioSystem.getClip()

    init {
//        background.apply {
//            open(AudioSystem.getAudioInputStream(resources.stream("/sounds/background.wav")))
//            loop(Clip.LOOP_CONTINUOUSLY)
//            addLineListener { isBackgroundMusicPlaying = it.type == LineEvent.Type.START }
//        }
//        intro.apply {
//            open(AudioSystem.getAudioInputStream(resources.stream("/sounds/intro.wav")))
//            addLineListener { isIntroMusicPlaying = it.type == LineEvent.Type.START }
//        }
    }

    fun toggleBackgroundMusic() = when {
        background.isRunning -> background.stop()
        else -> background.start()
    }

    fun toggleIntroMusic() = when {
        intro.isRunning -> intro.stop()
        else -> intro.start()
    }

    fun playBeep() {
        AudioClip(resources["/sounds/beep.wav"]).play()
    }

    fun playBoop() {
        AudioClip(resources["/sounds/boop.wav"]).play()
    }

}
