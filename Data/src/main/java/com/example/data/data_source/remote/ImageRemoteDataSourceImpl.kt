package com.example.data.data_source.remote

import com.example.common.extensions.suspendRunCatching
import com.example.data.data_source.mapper.toApiModel
import com.example.data.data_source.mapper.toDomainList
import com.example.domain.model.ImageModel
import com.example.network.api.ImageApi
import com.example.network.extenstions.suspendDataOrError
import javax.inject.Inject

class ImageRemoteDataSourceImpl @Inject constructor(
    private val imageService: ImageApi,
) : ImageRemoteDataSource {
    override suspend fun insertImage(image: ImageModel): Result<Unit> =
        suspendRunCatching {
            imageService.sendImage(image.toApiModel())
        }

    override suspend fun getDailyVoteUsersImages(): Result<List<ImageModel>> = suspendRunCatching {
        imageService.getDailyVoteUsersImages()
    }.mapCatching { response ->
        response.suspendDataOrError {
            it.toDomainList() ?: emptyList()
        }
    }

    override suspend fun votePositive() {
        imageService.votePositive()
    }

    override suspend fun voteNegative() {
        imageService.voteNegative()
    }
}
