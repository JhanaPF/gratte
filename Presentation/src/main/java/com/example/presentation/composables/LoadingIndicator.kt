package com.example.presentation.composables

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.presentation.R

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    @RawRes resId: Int = R.raw.loader,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LottieLoader(
            modifier = Modifier.align(Alignment.Center),
            resId = resId,
        )
    }
}
