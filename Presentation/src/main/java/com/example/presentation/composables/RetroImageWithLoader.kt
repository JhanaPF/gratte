package com.example.presentation.composables

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.R

@Composable
fun RetroImageWithLoader(
    modifier: Modifier = Modifier,
    image: Bitmap,
    borderColor: Color,
    isLoading: Boolean = false,
    onCloseClick: (() -> Unit)? = null,
    loader:
    @Composable()
    (BoxScope.() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .heightIn(max = 400.dp)
            .padding(vertical = 8.dp)
            .border(2.dp, borderColor),
    ) {
        Image(
            bitmap = image.asImageBitmap(),
            contentScale = ContentScale.Fit,
            contentDescription = "Pixelated Image",
            modifier = Modifier
                .align(Alignment.Center),
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
            loader()
        }
    }
}

@Preview
@Composable
fun RetroImageWithLoaderPreview() {
    val context = LocalContext.current
    val bitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.add_image_placeholder)

    RetroImageWithLoader(
        image = bitmap,
        borderColor = Color.Red,
        isLoading = true,
        onCloseClick = { },
    ) {
        RetroLoader()
    }
}
