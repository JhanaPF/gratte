package com.example.presentation.screens.imageView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ImageViewScreen(
    viewModel: ImageViewViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel
            .events
            .collect {
                when (it) {
                    is ImageViewEvents.NavigateBack -> {
                        navigateBack()
                    }
                }
            }
    }

    GalleryScreenContent(
        state = state,
        onDeleteClicked = viewModel::onDeleteClicked,
    )
}

@Composable
fun GalleryScreenContent(
    state: ImageViewUiState,
    onDeleteClicked: () -> Unit,
) {
}
