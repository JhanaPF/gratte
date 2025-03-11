package com.example.pixeliseit.presentation.screens.imagePicker

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pixeliseit.presentation.composables.RowSwitch
import com.example.pixeliseit.presentation.theme.PixeliseItTheme
import com.example.pixeliseit.presentation.utils.loadBitmapFromUri

private const val DEFAULT_PIXEL_SIZE = 1f

@Composable
fun ImagePickerScreen(
    popUp: () -> Boolean,
    viewModel: ImagePickerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        PixelatedImagePicker(
            state = state,
            modifier = Modifier.padding(innerPadding),
            onSelectImage = viewModel::onSelectImage,
            onPixelSizeChanged = viewModel::onPixelSizeChanged,
            onCrtToggled = viewModel::onCrtToggled,
        )
    }
}

@Composable
fun PixelatedImagePicker(
    state: ImagePickerUiState,
    modifier: Modifier = Modifier,
    onSelectImage: (Bitmap) -> Unit,
    onPixelSizeChanged: (Float) -> Unit,
    onCrtToggled: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    var crtEffect by remember { mutableStateOf(false) }
    var pixelSize by remember { mutableFloatStateOf(DEFAULT_PIXEL_SIZE) } // Adjust pixelation size

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            onSelectImage(
                uri?.let { loadBitmapFromUri(context, it) }
                    ?: return@rememberLauncherForActivityResult
            )
        }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Select Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        state.image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentScale = ContentScale.Fit,
                contentDescription = "Pixelated Image",
                modifier = Modifier
                    .heightIn(max = 500.dp)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
            )
        }

        Slider(
            modifier = Modifier
                .width(250.dp),
            value = pixelSize,
            onValueChange = {
                pixelSize = it
                onPixelSizeChanged(it)
            },
            valueRange = DEFAULT_PIXEL_SIZE..50f,
            steps = 9,
        )

        RowSwitch(
            modifier = Modifier
                .width(250.dp),
            label = "Crt Effect",
            checked = crtEffect
        ) {
            crtEffect = it
            onCrtToggled(it)
        }

        Text("Pixel Size: ${pixelSize.toInt()}", fontSize = 16.sp)
    }
}
