package com.example.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.R

@Composable
fun RetroBitmapWithLoader(
    image: ByteArray,
    borderColor: Color,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onCloseClick: (() -> Unit)? = null,
    loader: @Composable (BoxScope.() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .heightIn(max = 400.dp)
            .padding(vertical = 8.dp)
            .border(2.dp, borderColor),
    ) {
        ImageItem(
            base64Image = image,
            contentScale = ContentScale.Crop,
        )
        if (onCloseClick != null) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
                onClick = { onCloseClick() },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icn_quit),
                    contentDescription = "Refresh",
                )
            }
        }

        if (isLoading && loader != null) {
            // Just a little trick to avoid the loader from expanding the box
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .align(Alignment.Center),
            ) {
                loader()
            }
        }
    }
}

@Preview
@Composable
private fun RetroImageWithLoaderPreview() {
    val image = ByteArray(0)

    RetroBitmapWithLoader(
        image = image,
        borderColor = Color.Red,
        isLoading = true,
        onCloseClick = { },
    ) {
        LottieLoader(
            modifier = Modifier.align(Alignment.Center),
            resId = R.raw.loader,
        )
    }
}
