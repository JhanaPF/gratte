package com.example.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.presentation.R
import com.example.presentation.theme.retro

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    base64Image: ByteArray? = null,
    imageUrl: String? = null,
    score: Int? = null,
    contentScale: ContentScale = ContentScale.FillHeight,
) {
    val context = LocalContext.current
    val dataToLoad = imageUrl ?: base64Image

    val request = remember(dataToLoad) {
        ImageRequest.Builder(context)
            .data(dataToLoad)
            .crossfade(true)
            .build()
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            model = request,
            contentDescription = "Gallery Image",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentScale = contentScale,
            placeholder = painterResource(R.drawable.placeholder),
        )
        if (score != null) {
            Text(
                text = score.toString(),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = retro,
                fontSize = 24.sp,
            )
        }
    }
}
