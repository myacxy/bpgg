package net.myacxy.bpgg

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import kfoenix.JFXStylesheet
import tornadofx.*

class Styles : JFXStylesheet() {

    init {
        root {
            prefWidth = 1280.pt
            prefHeight = 720.pt
            fontFamily = "Roboto"
            backgroundColor = multi(Color.LIGHTSLATEGRAY.derive(0.88))
            title {
                fontSize = 14.pt
            }
        }

        navigationMenu {
            prefWidth = 256.pt
            backgroundColor = multi(Color.LIGHTSLATEGRAY)

            jfxButton {
                minWidth = 64.pt
                prefWidth = 256.pt
                minHeight = 36.pt
                padding = box(0.0.pt, 16.0.pt)
                alignment = Pos.CENTER_LEFT
                fontSize = 12.pt
                fontWeight = FontWeight.MEDIUM
                fontFamily = "Roboto Medium"
                graphicTextGap = 16.pt
                textFill = Color.WHITE

                glyphFont {
                    textFill = Color.WHITE
                }
            }
        }

        jfxDecorator {
            jfxDecoratorButtonsContainer {
                backgroundColor = multi(Color.DARKSLATEGRAY)
            }

            resizeBorder {
                borderColor = multi(box(Color.DARKSLATEGRAY))
                borderWidth = multi(box(0.pt, 2.pt, 2.pt, 2.pt))
            }
        }
    }


    companion object {
        val root by cssclass()
        val navigationMenu by cssclass()
        val glyphFont by cssclass("glyph-font")
    }

}
