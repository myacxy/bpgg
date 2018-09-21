package net.myacxy.bpgg

import com.jfoenix.controls.JFXDecorator
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import net.myacxy.bpgg.controllers.MainController
import net.myacxy.bpgg.views.MainView
import tornadofx.*

class BpggApp : App(MainView::class) {

    private val mainController: MainController by inject()
    private val iconFactory = MaterialDesignIconFactory.get()

    init {
        importStylesheet("/css/jfoenix-fonts.css")
        importStylesheet("/css/jfoenix-design.css")
        importStylesheet("/css/styles.css")

        reloadStylesheetsOnFocus()
    }

    override fun createPrimaryScene(view: UIComponent): Scene {
        return Scene(JFXDecorator(view.primaryStage, view.root).apply {
            (children[0] as HBox).children.add(2, jfxbutton {
                styleClass += "jfx-decorator-button"
                cursor = Cursor.HAND
                textFill = Color.WHITE
                ripplerFill = Color.WHITE
                translateX = -30.0
                iconFactory.setIcon(this, MaterialDesignIcon.SETTINGS, "16pt")
                action { mainController.toggleSettingsMenu() }
            }).also {
            }
        })
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch<BpggApp>(args)
        }

    }

}
