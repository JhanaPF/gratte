package com.example.data.data_source.remote

import com.example.domain.model.ImageModel

interface ImageRemoteDataSource {
    suspend fun insertImage(image: ImageModel): Result<Unit>
    suspend fun getDailyVoteUsersImages(): Result<List<ImageModel>>
    suspend fun votePositive()
    suspend fun voteNegative()
}
