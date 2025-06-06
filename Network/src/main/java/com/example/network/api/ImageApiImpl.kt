package com.example.network.api

import com.example.network.exception.createService
import com.example.network.model.ResponseApiModel
import com.example.network.model.image.ImageApiModel
import com.example.network.retrofit.ImageRetrofitService
import retrofit2.Retrofit
import javax.inject.Inject

class ImageApiImpl @Inject constructor(
    retrofit: Retrofit,
) : ImageApi {

    private val service: ImageRetrofitService by retrofit.createService()

    override suspend fun sendImage(image: ImageApiModel): ResponseApiModel<Unit> =
        service.sendImage(image)

    override suspend fun votePositive(): ResponseApiModel<Unit> =
        service.votePositive()

    override suspend fun voteNegative(): ResponseApiModel<Unit> =
        service.voteNegative()

    override suspend fun getDailyVoteUsersImages(): ResponseApiModel<List<ImageApiModel>> =
        service.getDailyVoteUsersImages()
}
