package com.example.data.provider

import android.graphics.Bitmap
import android.util.Base64
import com.example.domain.utils.BitmapConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.min

class BitmapConverterImpl @Inject constructor() : BitmapConverter {
    override suspend fun convert(
        bitmap: Bitmap,
        maxWidth: Int,
        maxHeight: Int,
    ): String = withContext(Dispatchers.Default) {
        val scaledBitmap = scaleBitmap(bitmap, maxWidth = maxWidth, maxHeight = maxHeight)
        bitmapToBase64(scaledBitmap)
    }

    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scale = min(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
        return if (scale < 1f) {
            Bitmap.createScaledBitmap(
                bitmap,
                (width * scale).toInt(),
                (height * scale).toInt(),
                true,
            )
        } else {
            bitmap
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        // Compress the bitmap as PNG (or JPEG if desired)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
