package com.example.domain.model

data class ImageModel(
    val image: String,
    val id: Int? = null,
    val userId: Int? = null,
    val score: Int? = 0,
)
