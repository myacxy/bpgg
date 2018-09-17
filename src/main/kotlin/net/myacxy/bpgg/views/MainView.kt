package net.myacxy.bpgg.views

import com.jfoenix.effects.JFXDepthManager
import javafx.scene.layout.BorderPane
import net.myacxy.bpgg.Styles
import tornadofx.*

class MainView : View() {

    override val root: BorderPane by fxml("/MainView.fxml")

    private val bpContent: BorderPane by fxid("bp_mv_content")
    private val sideMenuView: SideMenuView by inject()
    private val presentationView: PresentationView by inject()
    private val gameMasterView: GameMasterView by inject()

    init {
        with(root) {
            addClass(Styles.root)
            title = messages["title_app"]
            left = sideMenuView.root
        }

        with(bpContent) {
            center = presentationView.root.apply {
                maxHeightProperty().bind(root.heightProperty().multiply(0.66))
            }
            bottom = gameMasterView.root.apply {
                maxHeightProperty().bind(root.heightProperty().multiply(0.33))
            }
        }

        JFXDepthManager.setDepth(root.left, 2)
    }

}
