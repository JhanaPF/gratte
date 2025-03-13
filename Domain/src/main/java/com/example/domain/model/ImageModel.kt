package com.example.domain.model

data class ImageModel(
    val image: String,
    val id: Int? = null,
    val userId: String? = null,
    val score: Int? = 0,
)
