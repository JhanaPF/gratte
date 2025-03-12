package com.example.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.presentation.R

@Composable
fun RetroLoader(
    modifier: Modifier = Modifier,
) {
    val composition by
        rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))

    val progress by
        animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}
