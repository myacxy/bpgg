package net.myacxy.bpgg

import javafx.scene.text.TextAlignment
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.pt

class Styles : Stylesheet() {

    init {
        root {
            prefWidth = 1280.pt
            prefHeight = 720.pt
        }

        button {
            minWidth = 64.pt
            minHeight = 36.pt
            padding = box(0.0.pt, 16.0.pt)
            textAlignment = TextAlignment.CENTER
            fontSize = 12.pt
        }
    }

}
