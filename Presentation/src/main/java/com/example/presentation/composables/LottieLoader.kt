package com.example.presentation.composables

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    @RawRes resId: Int,
) {
    val composition by
        rememberLottieComposition(LottieCompositionSpec.RawRes(resId))

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
