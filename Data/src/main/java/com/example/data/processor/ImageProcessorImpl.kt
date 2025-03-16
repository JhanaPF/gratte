package com.example.data.processor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.data.provider.GPUImageProvider
import com.example.domain.gpui.ImageProcessor
import com.example.domain.model.ImageData
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.min

class ImageProcessorImpl @Inject constructor(
    private val gpuImageProvider: GPUImageProvider,
) : ImageProcessor {

    override suspend fun applyPixelation(image: ImageData, pixelSize: Float): ImageData =
        withContext(Dispatchers.Default) {
            val bitmap = imageDataToBitmap(image)
            val gpuImage = gpuImageProvider.create()
            gpuImage.setImage(bitmap)
            gpuImage.setFilter(GPUImagePixelationFilter().apply { setPixel(pixelSize) })
            val filteredBitmap = gpuImage.bitmapWithFilterApplied
            val scaledBitmap = scaleBitmap(filteredBitmap)
            bitmapToImageData(scaledBitmap)
        }

    override suspend fun applyCrt(image: ImageData): ImageData =
        withContext(Dispatchers.Default) {
            val bitmap = imageDataToBitmap(image)
            val gpuImage = gpuImageProvider.create()
            gpuImage.setImage(bitmap)
            gpuImage.setFilter(GPUImageCRTFilter())
            val filteredBitmap = gpuImage.bitmapWithFilterApplied
            val scaledBitmap = scaleBitmap(filteredBitmap)
            bitmapToImageData(scaledBitmap)
        }

    private fun imageDataToBitmap(imageData: ImageData): Bitmap {
        return BitmapFactory.decodeByteArray(imageData.bytes, 0, imageData.bytes.size)
    }

    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int = 800, maxHeight: Int = 800): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scale = min(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
        return if (scale < 1f) {
            Bitmap.createScaledBitmap(
                bitmap,
                (width * scale).toInt(),
                (height * scale).toInt(),
                true,
            )
        } else {
            bitmap
        }
    }

    private fun bitmapToImageData(bitmap: Bitmap): ImageData {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        return ImageData(outputStream.toByteArray())
    }
}
