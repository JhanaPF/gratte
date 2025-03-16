package com.example.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.example.domain.model.ImageData
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.random.Random

suspend fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? =
    withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

suspend fun bitmapToImageData(bitmap: Bitmap): ImageData =
    withContext(Dispatchers.Default) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        ImageData(outputStream.toByteArray())
    }

suspend fun loadImageDataFromUri(context: Context, uri: Uri): ImageData? {
    val bitmap = loadBitmapFromUri(context, uri)
    return bitmap?.let { bitmapToImageData(it) }
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
