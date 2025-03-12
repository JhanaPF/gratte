package com.example.domain.use_cases

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val DELIMITER = "base64"

class ConvertBase64ToBitmapUseCase @Inject constructor() {
    suspend operator fun invoke(base64Image: String): ByteArray = withContext(Dispatchers.Default) {
        val cleanBase64 = base64Image.substringAfter("$DELIMITER,")
        Base64.decode(cleanBase64, Base64.DEFAULT)
    }
}
