package com.example.domain.use_cases

import android.graphics.Bitmap
import android.util.Base64
import android.util.Base64.DEFAULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.min

class ConvertBitmapToBase64UseCase @Inject constructor() {
    suspend operator fun invoke(bitmap: Bitmap): String =
        // Heavy computation going on here i need the default dispatcher
        withContext(Dispatchers.Default) {
            // I unfortunately need to scale the bitmap to avoid "Row too big to fit into CursorWindow"..
            val scaledBitmap = scaleBitmap(bitmap, maxWidth = 800, maxHeight = 800)

            bitmapToBase64(scaledBitmap)
        }
}

fun scaleBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val scale = min(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
    return if (scale < 1f) {
        Bitmap.createScaledBitmap(bitmap, (width * scale).toInt(), (height * scale).toInt(), true)
    } else {
        bitmap
    }
}

fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    // Compress the bitmap as PNG or JPEG
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, DEFAULT)
}
