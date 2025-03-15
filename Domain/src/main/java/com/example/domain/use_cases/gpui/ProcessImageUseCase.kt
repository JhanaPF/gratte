package com.example.domain.use_cases.gpui

import android.graphics.Bitmap
import javax.inject.Inject

class ProcessImageUseCase @Inject constructor(
    private val applyPixelationEffectUseCase: ApplyPixelationEffectUseCase,
    private val applyCRTEffectUseCase: ApplyCRTEffectUseCase,
) {
    operator fun invoke(
        image: Bitmap,
        params: FilterParameters,
    ): Bitmap {
        val pixelated = applyPixelationEffectUseCase(image, params.pixelSize)
        return if (params.applyCrt) {
            applyCRTEffectUseCase(pixelated)
        } else {
            pixelated
        }
    }

    data class FilterParameters(
        val pixelSize: Float = 1f,
        val applyCrt: Boolean = false,
    )
}
