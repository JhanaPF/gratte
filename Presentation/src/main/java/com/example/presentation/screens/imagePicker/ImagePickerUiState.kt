package com.example.presentation.screens.imagePicker

import com.example.domain.model.ImageData

data class ImagePickerUiState(
    val image: ImageData? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
