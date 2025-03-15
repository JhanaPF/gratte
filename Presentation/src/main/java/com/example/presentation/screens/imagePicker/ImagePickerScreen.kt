package com.example.presentation.screens.imagePicker

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.utils.loadBitmapFromUri
import com.example.presentation.utils.randomFlashyColor
import com.example.presentation.R
import com.example.presentation.composables.NeonButtonSize
import com.example.presentation.composables.RetroBitmapWithLoader
import com.example.presentation.composables.LottieLoader
import com.example.presentation.composables.RetroNeonButton
import com.example.presentation.composables.RowSwitch
import com.example.presentation.theme.PixeliseItTheme
import com.example.presentation.theme.retro
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val DEFAULT_PIXEL_SIZE = 1f
private const val SLIDER_MAX = 50f
private const val IMAGE_PICKER_DEFAULT_FOLDER = "image/*"

@Composable
fun ImagePickerScreen(
    navigateBack: () -> Boolean,
    viewModel: ImagePickerViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.events
            .collectLatest { event ->
                when (event) {
                    is ImagePickerEvents.ShowErrorSnackBar -> {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = event.message,
                                duration = SnackbarDuration.Short,
                            )
                        }
                    }

                    ImagePickerEvents.NavigateBack -> {
                        navigateBack()
                    }
                }
            }
    }

    PixelatedImagePicker(
        state = state,
        snackBarHostState = snackBarHostState,
        onSelectImage = viewModel::onSelectImage,
        onPixelSizeChanged = viewModel::onPixelSizeChanged,
        onCrtToggled = viewModel::onCrtToggled,
        onSendImageClicked = viewModel::onSendImageClicked,
        onCloseImageClick = viewModel::onCloseImageClick,
    )
}

@Composable
fun PixelatedImagePicker(
    state: ImagePickerUiState,
    snackBarHostState: SnackbarHostState,
    onSelectImage: (Bitmap) -> Unit,
    onPixelSizeChanged: (Float) -> Unit,
    onCrtToggled: (Boolean) -> Unit,
    onSendImageClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onCloseImageClick: () -> Unit = {},
) {
    val context = LocalContext.current

    var crtEffect by remember { mutableStateOf(false) }
    var pixelSize by remember { mutableFloatStateOf(DEFAULT_PIXEL_SIZE) }

    var borderColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(Unit) {
        borderColor = randomFlashyColor()
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            onSelectImage(
                uri?.let { loadBitmapFromUri(context, it) }
                    ?: return@rememberLauncherForActivityResult,
            )
        }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { _ ->
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                text = stringResource(R.string.picker_title),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = retro,
                fontSize = 24.sp,
            )

            if (state.image != null) {
                RetroBitmapWithLoader(
                    image = state.image,
                    borderColor = borderColor,
                    isLoading = state.isLoading,
                    onCloseClick = onCloseImageClick,
                ) {
                    LottieLoader(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .heightIn(max = 400.dp)
                            .width(400.dp),
                        resId = R.raw.loader,
                    )
                }
            } else {
                Image(
                    painter = painterResource(id = R.drawable.add_image_placeholder),
                    contentScale = ContentScale.Inside,
                    contentDescription = "Pixelated Image",
                    modifier = Modifier
                        .defaultMinSize(minHeight = 400.dp, minWidth = 300.dp)
                        .padding(vertical = 8.dp)
                        .border(2.dp, borderColor)
                        .background(Color.LightGray),

                )
            }

            RetroNeonButton(
                text = stringResource(R.string.select_button_label),
                borderColor = borderColor,
                size = NeonButtonSize.Small,
                onClick = { imagePickerLauncher.launch(IMAGE_PICKER_DEFAULT_FOLDER) },
            )

            Slider(
                modifier = Modifier
                    .width(250.dp),
                value = pixelSize,
                onValueChange = {
                    pixelSize = it
                    onPixelSizeChanged(it)
                },
                valueRange = DEFAULT_PIXEL_SIZE..SLIDER_MAX,
                steps = 9,
            )

            Text(
                text = stringResource(R.string.slider_label, pixelSize.toInt()),
                fontFamily = retro,
                fontSize = 14.sp,
            )

            RowSwitch(
                modifier = Modifier.width(250.dp),
                label = stringResource(R.string.row_label),
                fontFamily = retro,
                checked = crtEffect,
            ) {
                crtEffect = it
                onCrtToggled(it)
            }

            RetroNeonButton(
                modifier = Modifier.padding(bottom = 16.dp),
                borderColor = borderColor,
                size = NeonButtonSize.Medium,
                text = stringResource(R.string.send_button_label),
                onClick = { onSendImageClicked() },
            )
        }
    }
}

@Preview
@Composable
private fun ImagePickerScreenPreview() {
    val dummyState = ImagePickerUiState(
        image = null,
        isLoading = false,
    )

    PixeliseItTheme {
        PixelatedImagePicker(
            state = dummyState,
            snackBarHostState = SnackbarHostState(),
            onSelectImage = {},
            onPixelSizeChanged = {},
            onCrtToggled = {},
            onSendImageClicked = {},
            onCloseImageClick = {},
        )
    }
}
