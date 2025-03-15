package com.example.presentation.screens.imageView

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.composables.ErrorContent
import com.example.presentation.composables.ImageItem
import com.example.presentation.theme.PixeliseItTheme
import com.example.presentation.utils.randomFlashyColor

@Composable
fun ImageViewScreen(
    navigateBack: () -> Unit,
    viewModel: ImageViewViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel
            .events
            .collect { event ->
                when (event) {
                    ImageViewEvents.NavigateBack -> navigateBack()
                }
            }
    }

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
    modifier: Modifier = Modifier,
) {
    when (state) {
        is ImageViewUiState.Loading -> {}
        is ImageViewUiState.Error -> {
            ErrorContent(message = state.message)
        }

        is ImageViewUiState.Success -> {
            ImageView(
                modifier = modifier,
                image = state.image,
                onImageClicked = onImageClicked,
                onCloseClick = onCloseClick,
                onDeleteClicked = onDeleteClicked,
            )
        }
    }
}

// If you wonder why i didn't reused the RetroBitmapWithLoader and make it evolve,
// it's because the GPUI filters return a bitmap by default..
// so to avoid a lot of other conversions i just created a new composable
@Composable
fun ImageView(
    image: ByteArray,
    onImageClicked: () -> Unit,
    onCloseClick: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var borderColor by remember { mutableStateOf(Color.White) }

    borderColor = randomFlashyColor()

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .border(2.dp, borderColor),
            contentAlignment = Alignment.Center,
        ) {
            ImageItem(
                modifier = Modifier
                    .clickable { onImageClicked() },
                contentScale = ContentScale.Fit,
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
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ImageViewScreenSuccessPreview() {
    // For preview, we supply a dummy byte array.
    // In a real scenario, you might load an actual image or use a placeholder.
    val dummyImage = ByteArray(0) // This may trigger your placeholder in ImageItem.
    val dummyState = ImageViewUiState.Success(image = dummyImage)

    PixeliseItTheme {
        GalleryScreenContent(
            state = dummyState,
            onImageClicked = {},
            onCloseClick = {},
            onDeleteClicked = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ImageViewScreenErrorPreview() {
    val dummyState = ImageViewUiState.Error(message = Throwable("An error occurred"))
    PixeliseItTheme {
        GalleryScreenContent(
            state = dummyState,
            onImageClicked = {},
            onCloseClick = {},
            onDeleteClicked = {},
        )
    }
}
