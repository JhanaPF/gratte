package com.example.domain.repository

import com.example.domain.model.HighScoresModel

interface HighScoresRepository {
    suspend fun insertHighScores(highScores: List<HighScoresModel>)
    suspend fun getHighScores(): Result<List<HighScoresModel>>
}
