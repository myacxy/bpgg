package net.myacxy.bpgg

import com.jfoenix.controls.JFXDecorator
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import kfoenix.jfxbutton
import net.myacxy.bpgg.controllers.MainController
import net.myacxy.bpgg.views.MainView
import org.jnativehook.GlobalScreen
import tornadofx.*
import java.util.logging.Level
import java.util.logging.Logger

class BpggApp : App(MainView::class) {

    private val mainController: MainController by inject()
    private val iconFactory = MaterialDesignIconFactory.get()

    init {
        reloadStylesheetsOnFocus()

        importStylesheet("/css/jfoenix-fonts.css")
        importStylesheet("/css/jfoenix-design.css")
        importStylesheet("/css/styles.css")
    }

    override fun start(stage: Stage) {
        Logger.getLogger(GlobalScreen::class.java.`package`.name).apply {
            level = Level.OFF
            useParentHandlers = false
        }
        GlobalScreen.registerNativeHook()
        super.start(stage)
    }

    override fun stop() {
        GlobalScreen.unregisterNativeHook()
        super.stop()
    }

    override fun createPrimaryScene(view: UIComponent): Scene {
        return Scene(JFXDecorator(view.primaryStage, view.root).apply {

            val settingsButton = jfxbutton {
                styleClass += "jfx-decorator-button"
                cursor = Cursor.HAND
                graphic = iconFactory.createIcon(MaterialDesignIcon.SETTINGS, "16")
                ripplerFill = Color.WHITE
                textFill = Color.WHITE
                translateX = -30.0
                action { mainController.toggleSettingsMenu() }
            }

            val presentationButton = jfxbutton {
                styleClass += "jfx-decorator-button"
                cursor = Cursor.HAND
                graphic = iconFactory.createIcon(MaterialDesignIcon.PRESENTATION, "16")
                textFill = Color.WHITE
                ripplerFill = Color.WHITE
                translateX = -30.0
                action { mainController.present() }
            }

            (children[0] as HBox).children.addAll(2, listOf(presentationButton, settingsButton))
        })
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch<BpggApp>(args)
        }
    }

}
