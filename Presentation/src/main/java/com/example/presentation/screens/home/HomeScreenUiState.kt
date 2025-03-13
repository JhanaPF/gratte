package com.example.presentation.screens.home

interface HomeScreenUiState {
    object Loading : HomeScreenUiState
    data class Success(val data: List<HighScoresItem>) : HomeScreenUiState
    data class Error(val message: Throwable) : HomeScreenUiState
}

data class HighScoresItem(
    val name: String,
    val pictureUrl: String,
    val score: Int,
)
