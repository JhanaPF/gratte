package com.example.data.data_source.remote

import com.example.domain.model.HighScoresModel

interface HighScoresRemoteDataSource {
    suspend fun getHighScores(): Result<List<HighScoresModel>>
}
