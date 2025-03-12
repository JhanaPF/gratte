package com.example.domain.use_cases

import android.graphics.Bitmap
import android.util.Base64
import android.util.Base64.DEFAULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ConvertBitmapToBase64UseCase @Inject constructor() {
    suspend operator fun invoke(bitmap: Bitmap): String =
        // Heavy computation going on here i need the default dispatcher
        withContext(Dispatchers.Default) {
            bitmapToBase64(bitmap)
        }
}

fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    // Compress the bitmap as PNG or JPEG
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, DEFAULT)
}
