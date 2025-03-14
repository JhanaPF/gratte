package com.example.network.api

import com.example.network.model.ResponseApiModel
import com.example.network.model.high_score.HighScoresApiModel

interface HighScoresApi {

    suspend fun getHighScores(): ResponseApiModel<List<HighScoresApiModel>>
}
