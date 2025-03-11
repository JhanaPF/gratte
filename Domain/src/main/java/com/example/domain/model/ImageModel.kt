package com.example.domain.model

import android.graphics.Bitmap

data class ImageModel(
    val id: Int? = null,
    val userId: Int? = null,
    val image: Bitmap,
    val score: Int? = 0
)