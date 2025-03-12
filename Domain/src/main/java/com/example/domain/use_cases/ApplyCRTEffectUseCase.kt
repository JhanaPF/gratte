package com.example.domain.use_cases

import android.graphics.Bitmap
import com.example.domain.gpui.GPUImageProvider
import com.example.domain.gpui.GPUImageCRTFilter
import javax.inject.Inject

class ApplyCRTEffectUseCase @Inject constructor(
    private val gpuImageProvider: GPUImageProvider,
) {
    operator fun invoke(image: Bitmap): Bitmap {
        val gpuImage = gpuImageProvider.create()
        gpuImage.setImage(image)
        gpuImage.setFilter(GPUImageCRTFilter())
        return gpuImage.bitmapWithFilterApplied
    }
}
