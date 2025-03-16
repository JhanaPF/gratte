package com.example.domain.use_cases.gpui

import com.example.domain.model.ImageData
import com.example.domain.gpui.ImageProcessor
import javax.inject.Inject

class ProcessImageUseCase @Inject constructor(
    private val imageProcessor: ImageProcessor,
) {
    suspend operator fun invoke(
        image: ImageData,
        params: FilterParameters,
    ): ImageData {
        val pixelated = imageProcessor.applyPixelation(image, params.pixelSize)
        return if (params.applyCrt) {
            imageProcessor.applyCrt(pixelated)
        } else {
            pixelated
        }
    }

    data class FilterParameters(
        val pixelSize: Float = 1f,
        val applyCrt: Boolean = false,
    )
}
