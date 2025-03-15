package com.example.presentation.screens.vote

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex
import kotlin.math.absoluteValue

@Composable
fun pagerModifier(offset: Float): Modifier = Modifier
    .zIndex(getZIndex(offset))
    .graphicsLayer {
        translationX = getTransition(
            size.width,
            offset,
        )
        rotationZ = getRotation(offset)
        alpha = getAlpha(offset)
        val scale = getScale(offset)
        scaleX = scale
        scaleY = scale
    }

private fun getAlpha(offset: Float): Float {
    return if (offset <= 0) 1f else 1f - offset.absoluteValue
}

private fun getTransition(
    pageWith: Float,
    offset: Float,
): Float {
    val transition = when {
        offset > 1f -> 0f
        offset > 0 -> pageWith * -offset
        else -> 0f
    }
    return transition
}

private fun getScale(offset: Float): Float {
    return when {
        offset > 1f -> 0f
        offset > -1f -> 1f - (offset * 0.1f)
        else -> 0f
    }
}

private fun getZIndex(offset: Float): Float {
    return -offset
}

private fun getRotation(offset: Float): Float {
    return if (offset > 0) 0f else offset * 10
}
