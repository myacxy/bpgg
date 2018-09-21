package net.myacxy.bpgg.views

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.scene.layout.VBox
import kfoenix.jfxbutton
import net.myacxy.bpgg.controllers.MainController
import tornadofx.*

class SideMenuView : View() {

    override val root = VBox()

    private val mainController: MainController by inject()

    init {
        val iconFactory = MaterialDesignIconFactory.get()

        root.apply {
            styleClass += "side-menu"

            // choose picture
            jfxbutton {
                text = messages["action_choose_picture"]
                graphic = iconFactory.createIcon(MaterialDesignIcon.IMAGE, "24")
                action { mainController.choosePicture() }
            }
        }
    }

}
