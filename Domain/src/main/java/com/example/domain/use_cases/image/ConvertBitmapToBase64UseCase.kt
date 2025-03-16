package com.example.domain.use_cases.image

import android.graphics.Bitmap
import com.example.domain.utils.BitmapConverter
import javax.inject.Inject

class ConvertBitmapToBase64UseCase @Inject constructor(
    private val bitmapConverter: BitmapConverter,
) {
    suspend operator fun invoke(bitmap: Bitmap): String {
        // Use the converter to perform scaling and conversion.
        return bitmapConverter.convert(bitmap)
    }
}
