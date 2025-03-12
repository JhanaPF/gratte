package com.example.presentation.screens.imageView

sealed interface ImageViewEvents {
    data object NavigateBack : ImageViewEvents
}
