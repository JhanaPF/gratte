package com.example.presentation.screens.gallery

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface GalleryUiState {
    data object Loading : GalleryUiState
    data object Empty : GalleryUiState
    data class Error(val message: Throwable) : GalleryUiState
    data class Success(
        val pictures: PersistentList<PictureItem> = persistentListOf(),
    ) : GalleryUiState
}

data class PictureItem(
    val id: Int,
    val imageArray: ByteArray,
    val score: Int = 0,
)
