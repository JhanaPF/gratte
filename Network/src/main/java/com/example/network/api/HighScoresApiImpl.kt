package com.example.network.api

import com.example.network.exception.createService
import com.example.network.model.ResponseApiModel
import com.example.network.model.high_score.HighScoresApiModel
import com.example.network.retrofit.HighScoreRetrofitService
import retrofit2.Retrofit
import javax.inject.Inject

class HighScoresApiImpl @Inject constructor(
    retrofit: Retrofit,
) : HighScoresApi {

    private val service: HighScoreRetrofitService by retrofit.createService()

    override suspend fun getHighScores(): ResponseApiModel<List<HighScoresApiModel>> =
        service.getHighScores()
}
