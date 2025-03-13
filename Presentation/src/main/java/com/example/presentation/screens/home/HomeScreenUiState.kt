package com.example.presentation.screens.home

import kotlinx.collections.immutable.PersistentList

interface HomeScreenUiState {
    object Loading : HomeScreenUiState
    data class Success(
        val highScores: PersistentList<HighScoresItem>,
        val personalBest: HighScoresItem?,
    ) : HomeScreenUiState

    data class Error(val message: Throwable) : HomeScreenUiState
}

data class HighScoresItem(
    val rank: String,
    val name: String,
    val pictureUrl: String?,
    val score: Int,
)
