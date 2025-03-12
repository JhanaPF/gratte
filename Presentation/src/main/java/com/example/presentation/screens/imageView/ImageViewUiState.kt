package com.example.presentation.screens.imageView

sealed interface ImageViewUiState {
    data class Success(
        val image: ByteArray,
    ) : ImageViewUiState

    data object Loading : ImageViewUiState
    data class Error(
        val message: Throwable,
    ) : ImageViewUiState
}
