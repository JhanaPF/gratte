package com.example.navigation.navigation
import kotlinx.serialization.Serializable

interface AppRoute {

    @Serializable
    object Home : AppRoute

    @Serializable
    object ImagePicker : AppRoute
}

val AppRoute.route: String
    get() = when (this) {
        AppRoute.Home -> "home"
        AppRoute.ImagePicker -> "image_picker"
        else -> "home"
    }