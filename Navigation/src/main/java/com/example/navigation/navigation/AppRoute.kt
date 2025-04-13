package com.example.navigation.navigation
import kotlinx.serialization.Serializable

interface AppRoute {

    @Serializable
    data object Home : AppRoute

    @Serializable
    object ImagePicker : AppRoute

    @Serializable
    object ImageVote : AppRoute

    @Serializable
    object Gallery : AppRoute

    @Serializable
    object Metronome : AppRoute
}
