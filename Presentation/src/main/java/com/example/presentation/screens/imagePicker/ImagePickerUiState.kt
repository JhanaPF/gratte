package com.example.presentation.screens.imagePicker

import android.graphics.Bitmap

data class ImagePickerUiState(
    val image: Bitmap? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)