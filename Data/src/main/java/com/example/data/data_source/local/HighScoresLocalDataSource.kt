package com.example.data.data_source.local

import com.example.domain.model.HighScoresModel

interface HighScoresLocalDataSource {
    suspend fun insertHighScores(highScores: List<HighScoresModel>)
    suspend fun getHighScores(): List<HighScoresModel>?
}
