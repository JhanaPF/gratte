package com.example.presentation.screens.imagePicker.navigation

import kotlinx.serialization.Serializable

// I Had to put it there to be able to use the savedStateHandle.toRoute because if it stays in AppRoute
// i would create a circle dependency between Navigation and Presentation
@Serializable
data class ImagePicker(val origin: Origin = Origin.NAVBAR) {
    companion object {
        enum class Origin {
            GALLERY,
            NAVBAR,
        }
    }
}
