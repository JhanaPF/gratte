package com.example.presentation.screens.imageView

import android.graphics.Bitmap

sealed interface ImageViewUiState {
    data class Image(
        val image: Bitmap,
    ) : ImageViewUiState

    data object Loading : ImageViewUiState
    data class Error(
        val message: String,
    ) : ImageViewUiState
}
