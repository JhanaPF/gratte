package com.example.network.retrofit

import com.example.network.extenstions.Constants
import com.example.network.model.ResponseApiModel
import com.example.network.model.image.HighScoresApiModel
import retrofit2.http.GET
import retrofit2.http.Headers

private const val HIGHSCORES_URL = "/highscores"

interface HighScoreRetrofitService {

    @GET(HIGHSCORES_URL)
    @Headers(Constants.HEADER_JSON)
    suspend fun getHighScores(): ResponseApiModel<List<HighScoresApiModel>>
}
