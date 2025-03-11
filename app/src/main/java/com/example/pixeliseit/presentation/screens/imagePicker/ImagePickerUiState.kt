package com.example.pixeliseit.presentation.screens.imagePicker

import android.graphics.Bitmap

data class ImagePickerUiState(
    val image: Bitmap? = null,
    val error: String? = null
)