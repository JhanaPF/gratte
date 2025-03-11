package com.example.pixeliseit.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.Color
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
    val hue = Random.nextInt(0, 360)      // Hue between 0 and 359
    val saturation = 0.9f                // Keep saturation high
    val value = 1.0f                     // Full brightness
    return Color.hsv(hue.toFloat(), saturation, value)
}