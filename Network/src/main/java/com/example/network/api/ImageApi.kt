package com.example.network.api

import com.example.network.model.ResponseApiModel
import com.example.network.model.image.ImageApiModel

interface ImageApi {

    suspend fun getDailyVoteUsersImages(): ResponseApiModel<List<ImageApiModel>>

    suspend fun sendImage(image: ImageApiModel): ResponseApiModel<Unit>

    suspend fun votePositive(): ResponseApiModel<Unit>
    suspend fun voteNegative(): ResponseApiModel<Unit>
}
