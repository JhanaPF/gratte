package com.example.pixeliseit.domain.use_cases

import android.graphics.Bitmap
import com.example.pixeliseit.data.gpui.GPUImageProvider
import com.example.pixeliseit.presentation.utils.GPUImageCRTFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter
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