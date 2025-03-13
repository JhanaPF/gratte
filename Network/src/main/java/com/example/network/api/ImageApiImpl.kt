package com.example.network.api

import com.example.network.di.qualifier.RetrofitCoroutineQualifier
import com.example.network.exception.createService
import com.example.network.model.ResponseApiModel
import com.example.network.model.image.HighScoresApiModel
import com.example.network.retrofit.ImageRetrofitService
import retrofit2.Retrofit
import javax.inject.Inject

class ImageApiImpl @Inject constructor(
    @RetrofitCoroutineQualifier retrofit: Retrofit,
) : ImageApi {

    private val service: ImageRetrofitService by retrofit.createService()

    override suspend fun getHighScores(): ResponseApiModel<List<HighScoresApiModel>> =
        service.getHighScores()
}
