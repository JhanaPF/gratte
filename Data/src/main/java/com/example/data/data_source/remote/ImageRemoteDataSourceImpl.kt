package com.example.data.data_source.remote

import com.example.data.data_source.mapper.toDomain
import com.example.domain.model.HighScoresModel
import com.example.network.api.ImageApi
import com.example.network.extenstions.dataOrError
import javax.inject.Inject

class ImageRemoteDataSourceImpl @Inject constructor(
    private val imageApi: ImageApi,
) : ImageRemoteDataSource {

    override suspend fun getHighScores(): List<HighScoresModel> =
        imageApi.getHighScores().dataOrError {
            it.toDomain()
        }
}
