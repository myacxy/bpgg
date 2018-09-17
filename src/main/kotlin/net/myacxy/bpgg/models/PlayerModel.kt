package net.myacxy.bpgg.models

import tornadofx.*

class PlayerModel : ItemViewModel<Player>() {
    val name = bind { item?.nameProperty() }
    val score = bind { item?.scoreProperty() }
}
