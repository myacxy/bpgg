package net.myacxy.bpgg

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import kfoenix.JFXStylesheet
import tornadofx.*

class Styles : JFXStylesheet() {

    init {
        root {
            prefWidth = 1280.pt
            prefHeight = 720.pt
            fontFamily = "Roboto"
        }

        navigationMenu {
            prefWidth = 256.pt

            and(jfxButton) {
                minWidth = 64.pt
                prefWidth = 256.pt
                minHeight = 36.pt
                padding = box(0.0.pt, 16.0.pt)
                alignment = Pos.CENTER_LEFT
                fontSize = 12.pt
                fontWeight = FontWeight.MEDIUM
                graphicTextGap = 16.pt
            }
        }

    }


    companion object {
        val root by cssclass()
        val navigationMenu by cssclass()
    }

}
