package com.example.domain.utils

import android.graphics.Bitmap

// Domain layer: Define the abstraction
interface BitmapConverter {
    suspend fun convert(bitmap: Bitmap, maxWidth: Int = 800, maxHeight: Int = 800): String
}
