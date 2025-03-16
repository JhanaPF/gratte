package com.example.domain.gpui

import com.example.domain.model.ImageData

interface ImageProcessor {
    suspend fun applyPixelation(image: ImageData, pixelSize: Float): ImageData
    suspend fun applyCrt(image: ImageData): ImageData
}
