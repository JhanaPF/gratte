package com.example.pixeliseit.domain.use_cases

import android.graphics.Bitmap
import com.example.pixeliseit.data.gpui.GPUImageProvider
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter
import javax.inject.Inject

class ApplyPixelationEffectUseCase @Inject constructor(
    private val gpuImageProvider: GPUImageProvider,
) {
    operator fun invoke(image: Bitmap, pixelSize: Float): Bitmap {
        val gpuImage = gpuImageProvider.create()
        gpuImage.setImage(image)
        gpuImage.setFilter(GPUImagePixelationFilter().apply { setPixel(pixelSize) })
        return gpuImage.bitmapWithFilterApplied
    }
}