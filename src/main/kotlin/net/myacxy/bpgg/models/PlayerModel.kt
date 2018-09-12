package net.myacxy.bpgg.models

import tornadofx.ItemViewModel
import tornadofx.toProperty

class PlayerModel : ItemViewModel<Player>() {
    val name = bind { item?.name?.toProperty() }
    val score = bind { item?.score?.toProperty() }
}
