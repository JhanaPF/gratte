package com.example.data.data_source.remote

import com.example.domain.model.HighScoresModel

interface ImageRemoteDataSource {
    suspend fun getHighScores(): List<HighScoresModel>
}
