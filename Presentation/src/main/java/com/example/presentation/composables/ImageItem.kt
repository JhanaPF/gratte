package com.example.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.presentation.R

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    base64Image: ByteArray? = null,
    imageUrl: String? = null,
) {
    val context = LocalContext.current
    val dataToLoad = imageUrl ?: base64Image

    val request = remember(dataToLoad) {
        ImageRequest.Builder(context)
            .data(dataToLoad)
            .crossfade(true)
            .build()
    }
    AsyncImage(
        model = request,
        contentDescription = "Gallery Image",
        modifier = modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.placeholder),
    )
}
