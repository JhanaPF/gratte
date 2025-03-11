package com.example.domain.model

import android.graphics.Bitmap

data class ImageModel(
    val id: Int,
    val userId: Int,
    val image: Bitmap,
    val score: Int
)