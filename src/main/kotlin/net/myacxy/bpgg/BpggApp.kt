package net.myacxy.bpgg

import net.myacxy.bpgg.views.MainView
import tornadofx.App
import tornadofx.launch

class BpggApp : App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    launch<BpggApp>(args)
}
