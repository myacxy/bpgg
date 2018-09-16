package net.myacxy.bpgg

import com.jfoenix.controls.JFXDecorator
import javafx.scene.Scene
import net.myacxy.bpgg.views.MainView
import tornadofx.*

class BpggApp : App(MainView::class, Styles::class) {

    override fun createPrimaryScene(view: UIComponent): Scene {
        return Scene(JFXDecorator(view.primaryStage, view.root))
    }
}

fun main(args: Array<String>) {
    launch<BpggApp>(args)
}
