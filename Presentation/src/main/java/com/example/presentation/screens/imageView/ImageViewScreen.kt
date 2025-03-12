package com.example.presentation.screens.imageView

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.composables.ErrorView
import com.example.presentation.composables.ImageItem

@Composable
fun ImageViewScreen(
    viewModel: ImageViewViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GalleryScreenContent(
        state = state,
        onImageClicked = { navigateBack() },
        onCloseClick = { navigateBack() },
        onDeleteClicked = viewModel::onDeleteClicked,
    )
}

@Composable
fun GalleryScreenContent(
    state: ImageViewUiState,
    onImageClicked: () -> Unit,
    onCloseClick: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    when (state) {
        is ImageViewUiState.Loading -> {}
        is ImageViewUiState.Error -> {
            ErrorView(message = state.message)
        }

        is ImageViewUiState.Success -> {
            ImageView(
                modifier = Modifier,
                image = state.image,
                onImageClicked = onImageClicked,
                onCloseClick = onCloseClick,
                onDeleteClicked = onDeleteClicked,
            )
        }
    }
}

// If you wonder why i didn't reused the RetroBitmapWithLoader and make it evolve,
// it's because the GPUI filter return a bitmap by default..
// so to avoid a lot of other conversions i just created a new composable
@Composable
fun ImageView(
    modifier: Modifier = Modifier,
    image: ByteArray,
    onImageClicked: () -> Unit,
    onCloseClick: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    val borderColor by remember { mutableStateOf(Color.White) }

    Box(
        modifier = modifier
            .wrapContentSize()
            .border(2.dp, borderColor),
    ) {
        ImageItem(
            modifier = Modifier
                .clickable { onImageClicked() },
            base64Image = image,
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp),
            onClick = { onCloseClick() },
        ) {
            Image(
                painter = painterResource(id = R.drawable.icn_quit),
                contentDescription = "Quit",
            )
        }
        IconButton(
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.BottomEnd),
            onClick = { onDeleteClicked() },
        ) {
            Image(
                painter = painterResource(id = R.drawable.icn_trashbin),
                contentDescription = "Delete",
            )
        }
    }
}
