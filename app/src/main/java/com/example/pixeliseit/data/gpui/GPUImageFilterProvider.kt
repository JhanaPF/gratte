package com.example.pixeliseit.data.gpui

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.co.cyberagent.android.gpuimage.GPUImage
import javax.inject.Inject

interface GPUImageProvider {
    fun create(): GPUImage
}

class GPUImageFilterProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : GPUImageProvider {
    override fun create(): GPUImage = GPUImage(context)
}