package com.example.domain.gpui

import jp.co.cyberagent.android.gpuimage.GPUImage

interface GPUImageProvider {
    fun create(): GPUImage
}