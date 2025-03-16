package com.example.data.provider

import jp.co.cyberagent.android.gpuimage.GPUImage

interface GPUImageProvider {
    fun create(): GPUImage
}
