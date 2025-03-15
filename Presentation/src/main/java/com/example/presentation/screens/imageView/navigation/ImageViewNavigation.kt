package com.example.presentation.screens.imageView.navigation

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

// I Had to put it there to be able to use the savedStateHandle.toRoute because if it stays in AppRoute
// i would create a circle dependency between Navigation and Presentation
@Keep
@Serializable
data class ImageView(val imageId: Int)
