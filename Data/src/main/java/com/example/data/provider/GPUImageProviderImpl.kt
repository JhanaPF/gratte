package com.example.data.provider

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.co.cyberagent.android.gpuimage.GPUImage
import javax.inject.Inject

class GPUImageProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : GPUImageProvider {
    override fun create(): GPUImage = GPUImage(context)
}
