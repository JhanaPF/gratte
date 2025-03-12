package com.example.presentation.screens.gallery

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.composables.ImageItem
import com.example.presentation.composables.LottieLoader
import com.example.presentation.theme.retro
import com.example.presentation.utils.randomFlashyColor

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel = hiltViewModel(),
    navigateToImageView: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel
            .events
            .collect {
                when (it) {
                    is GalleryEvents.NavigateToPicture -> {
                        navigateToImageView()
                    }
                }
            }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.gallery_title),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = retro,
            fontSize = 24.sp,
        )

        GalleryScreenContent(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            state = state,
            onImageClick = viewModel::onImageClick,
        )
    }
}

@Composable
fun GalleryScreenContent(
    modifier: Modifier = Modifier,
    state: GalleryUiState,
    onImageClick: () -> Unit,
) {
    when (state) {
        GalleryUiState.Loading -> LoadingIndicator()
        is GalleryUiState.Error -> ErrorView(message = state.message)
        is GalleryUiState.Success -> ImageGrid(
            modifier = modifier,
            state = state,
            onImageClick = onImageClick,
        )
    }
}

@Composable
fun ImageGrid(
    modifier: Modifier = Modifier,
    state: GalleryUiState.Success,
    onImageClick: () -> Unit,
) {
    var borderColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(Unit) {
        borderColor = randomFlashyColor()
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 16.dp)
            .border(2.dp, borderColor),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(state.pictures, key = { it.hashCode() }) { picture ->
                ImageItem(
                    modifier = Modifier
                        .clickable { onImageClick() },
                    base64Image = picture,
                )
            }
        }
    }
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        LottieLoader(
            modifier = Modifier.align(Alignment.Center),
            resId = R.raw.loader,
        )
    }
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: Throwable,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
            )
            Text(
                text = message.message ?: stringResource(R.string.unknown_error),
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
