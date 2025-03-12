package com.example.presentation.screens.gallery

sealed interface GalleryEvents {
    data object NavigateToPicture : GalleryEvents
}
