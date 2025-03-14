package com.example.data.data_source.remote

import com.example.common.extensions.suspendRunCatching
import com.example.data.data_source.mapper.toApiModel
import com.example.domain.model.ImageModel
import com.example.network.api.ImageApi
import javax.inject.Inject

class ImageRemoteDataSourceImpl @Inject constructor(
    private val imageService: ImageApi,
) : ImageRemoteDataSource {
    override suspend fun insertImage(image: ImageModel): Result<Unit> =
        suspendRunCatching {
            imageService.sendImage(image.toApiModel())
        }
}
