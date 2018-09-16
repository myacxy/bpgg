package net.myacxy.bpgg.views

import javafx.scene.layout.VBox
import kfoenix.jfxbutton
import net.myacxy.bpgg.Styles
import net.myacxy.bpgg.controllers.SideMenuController
import net.myacxy.bpgg.models.SideMenuEvent
import org.controlsfx.glyphfont.FontAwesome
import org.controlsfx.glyphfont.Glyph
import tornadofx.*

class SideMenuView : View() {

    override val root = VBox()

    private val sideMenuController: SideMenuController by inject()

    init {
        root.apply {
            addClass(Styles.navigationMenu)

            // choose picture
            jfxbutton {
                addClass(Styles.navigationMenu)
                text = messages["action_choose_picture"]
                graphic = Glyph(FONT_FAMILY_GLYPH, GLYPH_IMAGE)
                action { sideMenuController.onNavigationEvent(SideMenuEvent.ChoosePicture) }
            }

            // reveal picture
            jfxbutton {
                addClass(Styles.navigationMenu)
                text = messages["action_reveal_picture"]
                graphic = Glyph(FONT_FAMILY_GLYPH, GLYPH_IMAGE)
                action { sideMenuController.onNavigationEvent(SideMenuEvent.RevealPicture) }
            }

            // present
            jfxbutton {
                addClass(Styles.navigationMenu)
                text = messages["action_present"]
                graphic = Glyph(FONT_FAMILY_GLYPH, GLYPH_TV)
                action { sideMenuController.onNavigationEvent(SideMenuEvent.Present) }
            }

        }
    }

    private companion object {
        const val FONT_FAMILY_GLYPH = "FontAwesome"
        const val GLYPH_TV = "\uf26c"

        val GLYPH_IMAGE = FontAwesome.Glyph.IMAGE
    }

}
