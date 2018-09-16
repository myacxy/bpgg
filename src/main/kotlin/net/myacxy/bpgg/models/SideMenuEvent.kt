package net.myacxy.bpgg.models

sealed class SideMenuEvent {
    object ChoosePicture : SideMenuEvent()
    object Present : SideMenuEvent()
    object RevealPicture : SideMenuEvent()
}
