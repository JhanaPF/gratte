package com.example.presentation.screens.vote

import kotlinx.collections.immutable.PersistentList

interface ImageVoteScreenUiState {
    object Loading : ImageVoteScreenUiState
    object EndOfProfiles : ImageVoteScreenUiState

    // As an example on this screen we will pass lambdas to the UIState as parameters instead of going all the way up in Composables
    // this is debatable and can be done in different ways, but this is a simple way to do it that is like (keep it simple)
    data class Success(
        val pagerItems: PersistentList<PagerItem>,
        val onLastProfile: () -> Unit,
        val onVotePositive: () -> Unit,
        val onVoteNegative: () -> Unit,
    ) : ImageVoteScreenUiState

    data class Error(val message: Throwable) : ImageVoteScreenUiState
}

data class PagerItem(
    val name: String,
    val pictureUrl: String?,
    val score: Int,
)
