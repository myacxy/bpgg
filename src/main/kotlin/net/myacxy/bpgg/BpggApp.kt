package net.myacxy.bpgg

import com.jfoenix.controls.JFXDecorator
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import net.myacxy.bpgg.controllers.MainController
import net.myacxy.bpgg.views.MainView
import org.controlsfx.glyphfont.FontAwesome
import tornadofx.*
import tornadofx.controlsfx.glyph

class BpggApp : App(MainView::class, Styles::class) {

    private val mainController: MainController by inject()

    override fun createPrimaryScene(view: UIComponent): Scene {
        return Scene(JFXDecorator(view.primaryStage, view.root).apply {
            (children[0] as HBox).children.add(2, jfxbutton {
                styleClass += "jfx-decorator-button"
                cursor = Cursor.HAND
                graphic = glyph {
                    fontFamily = "FontAwesome"
                    icon = FontAwesome.Glyph.COG
                    setColor(Color.WHITE)
                    size(16.0)
                }
                ripplerFill = Color.WHITE
                translateX = -30.0

                action { mainController.toggleSettingsMenu() }
            })
        })
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch<BpggApp>(args)
        }

    }

}
