package com.example.domain.model

data class HighScoresModel(
    val id: String,
    val rank: String,
    val userid: String,
    val score: Int,
    val pictureUrl: String?,
)
