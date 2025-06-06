package com.example.network.model.high_score

import kotlinx.serialization.Serializable

@Serializable
data class HighScoresApiModel(
    val id: String? = null,
    val rank: String? = null,
    val userid: String? = null,
    val score: String? = null,
    val picture_url: String? = null,
)
