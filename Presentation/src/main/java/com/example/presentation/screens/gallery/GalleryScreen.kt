package com.example.presentation.screens.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.composables.ErrorContent
import com.example.presentation.composables.ImageItem
import com.example.presentation.composables.LoadingIndicator
import com.example.presentation.theme.retro
import com.example.presentation.utils.randomFlashyColor

@Composable
fun GalleryScreen(
    navigateToImageView: (Int) -> Unit,
    navigateToImagePicker: () -> Unit,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val openAlertDialog = remember { mutableStateOf(false) }

    if (openAlertDialog.value) {
        DeleteConfirmDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = { viewModel.onDeleteClick() },
            dialogTitle = stringResource(R.string.delete_all_images_title),
            dialogText = stringResource(R.string.delete_all_images_text),
            icon = Icons.Default.Warning,
        )
    }

    GalleryScreenContent(
        state = state,
        onImageClick = { navigateToImageView(it) },
        onDeleteClick = { openAlertDialog.value = true },
        onAddClick = navigateToImagePicker,
    )
}

@Composable
fun GalleryScreenContent(
    state: GalleryUiState,
    onImageClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
) {
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

        when (state) {
            GalleryUiState.Loading -> LoadingIndicator()
            is GalleryUiState.Error -> ErrorContent(throwable = state.message)
            is GalleryUiState.Success -> ImageGrid(
                modifier = modifier,
                state = state,
                onImageClick = onImageClick,
                onDeleteClick = onDeleteClick,
            )

            GalleryUiState.Empty -> EmptyView(
                modifier = modifier,
                onAddClick,
            )
        }
    }
}

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }

    // When this composable enters composition, trigger the animation.
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn() + scaleIn(initialScale = 0.7f),
        exit = fadeOut() + scaleOut(targetScale = 0.7f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(R.string.empty_text),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontFamily = retro,
            )
            IconButton(
                modifier = Modifier.size(128.dp),
                onClick = { onAddClick() },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icn_add),
                    contentDescription = "Add",
                )
            }
        }
    }
}

@Composable
fun ImageGrid(
    state: GalleryUiState.Success,
    onImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
) {
    var borderColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(Unit) {
        borderColor = randomFlashyColor()
    }

    Box(
        modifier = modifier
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
                        .clickable { onImageClick(picture.id) },
                    contentScale = ContentScale.FillHeight,
                    score = picture.score,
                    base64Image = picture.imageArray,
                )
            }
        }
        IconButton(
            modifier = Modifier
                .size(84.dp)
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 8.dp),
            onClick = { onDeleteClick() },
        ) {
            Image(
                painter = painterResource(id = R.drawable.icn_trashbin),
                contentDescription = "Delete",
            )
        }
    }
}

@Composable
fun DeleteConfirmDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Alert Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(
                text = dialogText,
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                    onDismissRequest()
                },
            ) {
                Text(stringResource(R.string.dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text(stringResource(R.string.dialog_dismiss))
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun GalleryScreenContentEmptyPreview() {
    val dummyState = GalleryUiState.Empty

    GalleryScreenContent(
        state = dummyState,
        onImageClick = {},
        onDeleteClick = {},
        onAddClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun GalleryScreenContentErrorPreview() {
    val dummyState = GalleryUiState.Error(Throwable("An error occurred"))

    GalleryScreenContent(
        state = dummyState,
        onImageClick = {},
        onDeleteClick = {},
        onAddClick = {},
    )
}
