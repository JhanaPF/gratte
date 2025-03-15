package com.example.presentation.screens.imagePicker.navigation

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

// I Had to put it there to be able to use the savedStateHandle.toRoute because if it stays in AppRoute
// i would create a circle dependency between Navigation and Presentation
@Keep
@Serializable
data class ImagePicker(val origin: Origin = Origin.NAVBAR) {
    companion object {
        @Keep
        enum class Origin {
            GALLERY,
            NAVBAR,
        }
    }
}
