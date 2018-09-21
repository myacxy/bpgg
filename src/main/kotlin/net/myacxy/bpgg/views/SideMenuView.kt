package net.myacxy.bpgg.views

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.scene.layout.VBox
import kfoenix.jfxbutton
import net.myacxy.bpgg.controllers.SideMenuController
import net.myacxy.bpgg.models.SideMenuEvent
import tornadofx.*

class SideMenuView : View() {

    override val root = VBox()

    private val sideMenuController: SideMenuController by inject()

    init {
        val iconFactory = MaterialDesignIconFactory.get()

        root.apply {
            styleClass += "side-menu"

            // choose picture
            jfxbutton {
                text = messages["action_choose_picture"]
                graphic = iconFactory.createIcon(MaterialDesignIcon.IMAGE, "24")
                action { sideMenuController.onNavigationEvent(SideMenuEvent.ChoosePicture) }
            }

            // reveal picture
            jfxbutton {
                text = messages["action_reveal_picture"]
                graphic = iconFactory.createIcon(MaterialDesignIcon.IMAGE, "24")
                action { sideMenuController.onNavigationEvent(SideMenuEvent.RevealPicture) }
            }

            // present
            jfxbutton {
                text = messages["action_present"]
                graphic = iconFactory.createIcon(MaterialDesignIcon.PRESENTATION, "24")
                action { sideMenuController.onNavigationEvent(SideMenuEvent.Present) }
            }

        }
    }
}
