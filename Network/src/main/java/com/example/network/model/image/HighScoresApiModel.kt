package com.example.network.model.image

import kotlinx.serialization.Serializable

@Serializable
data class HighScoresApiModel(
    val id: String? = null,
    val userid: String? = null,
    val score: String? = null,
    val picture_url: String? = null,
)
