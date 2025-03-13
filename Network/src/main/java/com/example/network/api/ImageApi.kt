package com.example.network.api

import com.example.network.model.ResponseApiModel
import com.example.network.model.image.HighScoresApiModel

interface ImageApi {

    suspend fun getHighScores(): ResponseApiModel<List<HighScoresApiModel>>
}
