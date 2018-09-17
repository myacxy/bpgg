package net.myacxy.bpgg.controllers

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class SettingsController : Controller() {

    val minimumBlurProperty = SimpleIntegerProperty(20)
    var minimumBlur by minimumBlurProperty

    val maximumBlurProperty = SimpleIntegerProperty(63)
    var maximumBlur by maximumBlurProperty

}
