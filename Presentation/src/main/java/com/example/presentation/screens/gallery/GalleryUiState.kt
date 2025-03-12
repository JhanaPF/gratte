package com.example.presentation.screens.gallery

import android.graphics.Bitmap
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface GalleryUiState {
    data object Loading : GalleryUiState
    data object Error : GalleryUiState
    data class Success(
        val image: PersistentList<Bitmap> = persistentListOf(),
    ) : GalleryUiState
}
