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
    object ImageView : AppRoute
}

val AppRoute.route: String
    get() = when (this) {
        AppRoute.Home -> "home"
        AppRoute.ImagePicker -> "image_picker"
        else -> "home"
    }
