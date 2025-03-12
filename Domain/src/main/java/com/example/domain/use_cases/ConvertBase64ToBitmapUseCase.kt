package com.example.domain.use_cases

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConvertBase64ToBitmapUseCase @Inject constructor() {
    suspend operator fun invoke(base64Img: String): Bitmap =
        // Heavy computation going on here i need the default dispatcher
        withContext(Dispatchers.Default) {
            base64ToBitmap(base64Img)
        }
}

fun base64ToBitmap(base64String: String): Bitmap {
    val byteArray = Base64.decode(base64String, DEFAULT)
    return BitmapFactory.decodeByteArray(
        byteArray,
        0,
        byteArray.size,
    )
}
