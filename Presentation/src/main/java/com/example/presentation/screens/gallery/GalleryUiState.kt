package com.example.presentation.screens.gallery

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface GalleryUiState {
    data object Loading : GalleryUiState
    data class Error(val message: Throwable) : GalleryUiState
    data class Success(
        val pictures: PersistentList<ByteArray> = persistentListOf(),
    ) : GalleryUiState
}
