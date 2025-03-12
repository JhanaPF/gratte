package com.example.navigation.navigation
import kotlinx.serialization.Serializable

interface AppRoute {

    @Serializable
    object Home : AppRoute

    @Serializable
    object ImagePicker : AppRoute

    @Serializable
    object Gallery : AppRoute

    @Serializable
    data class ImageView(val imageId: Int) : AppRoute
}
