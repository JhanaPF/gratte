package com.example.domain.model

data class ImageModel(
    val id: Int? = null,
    val userId: Int? = null,
    val image: String,
    val score: Int? = 0,
)
