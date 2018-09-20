package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXDrawer
import com.jfoenix.effects.JFXDepthManager
import javafx.scene.layout.BorderPane
import net.myacxy.bpgg.Styles
import tornadofx.*

class MainView : View() {

    override val root: BorderPane by fxml("/MainView.fxml")

    private val drawerSettings: JFXDrawer by fxid("drawer")
    private val bpContent: BorderPane by fxid("content")
    private val sideMenuView: SideMenuView by inject()
    private val presentationView: PresentationView by inject()
    private val gameMasterView: GameMasterView by inject()
    private val settingsView: SettingsView by inject()

    init {
        with(root) {
            addClass(Styles.root)
            prefWidth = 1280.0
            prefHeight = 720.0
            title = messages["title_app"]

            left = sideMenuView.root
        }

        with(drawerSettings) {
            sidePane += settingsView.root
        }

        with(bpContent) {
            center = presentationView.root.apply {
                maxHeightProperty().bind(root.heightProperty().multiply(0.5))
            }
            bottom = gameMasterView.root.apply {
                maxHeightProperty().bind(root.heightProperty().multiply(0.5))
            }
        }

        JFXDepthManager.setDepth(root.left, 2)
    }

    fun toggleDrawer() = if (drawerSettings.isClosed || drawerSettings.isClosing) {
        drawerSettings.open()
    } else {
        drawerSettings.close()
    }

}
