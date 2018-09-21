package net.myacxy.bpgg.views

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import kfoenix.jfxbutton
import net.myacxy.bpgg.controllers.GameController
import net.myacxy.bpgg.controllers.MainController
import net.myacxy.bpgg.models.GameEvent
import tornadofx.*

class SideMenuView : View() {

    override val root = VBox()

    private val mainController: MainController by inject()
    private val gameController: GameController by inject()

    init {
        val iconFactory = MaterialDesignIconFactory.get()

        root.apply {
            styleClass += "side-menu"

            // choose pictures
            jfxbutton {
                styleClass += "button-contained-icon"
                text = messages["action_choose_pictures"]
                graphic = iconFactory.createIcon(MaterialDesignIcon.IMAGE_MULTIPLE, "24")
                action { mainController.choosePictures() }
            }

            datagrid(gameController.pictures) {
                vboxConstraints { vgrow = Priority.ALWAYS }
                maxWidth = 256.0
                cellWidth = 224.0
                cellHeight = 224.0
                multiSelect = false
                isPickOnBounds = true
                cellCache {
                    vbox(8.0, Pos.CENTER) {
                        imageview(it, true) {
                            isCache = true
                            isPreserveRatio = true
                            isPickOnBounds = true
                            fitHeight = 192.0
                            fitWidth = 192.0
                        }
                        label(it.split("\\").last()) {
                            padding = insets(16.0, 0)
                        }
                    }
                }
                onUserSelect { gameController.onGameEvent(GameEvent.PictureSelect(it)) }
            }
        }
    }

}
