package com.example.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.example.presentation.theme.Blue
import com.example.presentation.theme.BlueViolet
import com.example.presentation.theme.Cyan
import com.example.presentation.theme.Green
import com.example.presentation.theme.GreenYellow
import com.example.presentation.theme.HotPink
import com.example.presentation.theme.Magenta
import com.example.presentation.theme.Orange
import com.example.presentation.theme.Red
import com.example.presentation.theme.Tealish
import java.io.InputStream
import kotlin.random.Random

fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun randomFlashyColor(): Color {
    val hue = Random.nextInt(0, 360)
    val saturation = 0.9f
    val value = 1.0f
    return Color.hsv(hue.toFloat(), saturation, value)
}

private val rankColors = listOf(
    Red,
    Green,
    Tealish,
    Cyan,
    Magenta,
    Orange,
    Blue,
    HotPink,
    BlueViolet,
    GreenYellow,
)

fun colorForRank(index: Int): Color =
    rankColors.getOrElse(index) { Green }
