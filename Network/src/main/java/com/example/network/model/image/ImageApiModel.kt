package com.example.network.model.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageApiModel(
    val image: String? = null,
    val id: Int? = null,
    val userId: String? = null,
    val score: Int? = 0,
)
