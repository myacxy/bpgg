package net.myacxy.bpgg.views

import com.jfoenix.controls.JFXTextField
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import net.myacxy.bpgg.models.PlayerScope
import tornadofx.*

class PlayerSettingsView : View() {

    override val root: VBox by fxml("/PlayerSettingsView.fxml")
    override val scope = super.scope as PlayerScope

    private val lblTitle: Label by fxid("title")
    private val tfName: JFXTextField by fxid("name")

    init {
        lblTitle.text = scope.title
        tfName.textProperty().bindBidirectional(scope.model.name)
    }

}
