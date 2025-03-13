package com.example.data.data_source.remote

import com.example.common.extensions.suspendRunCatching
import com.example.data.data_source.mapper.toDomainModel
import com.example.domain.model.HighScoresModel
import com.example.network.api.HighScoresApi
import com.example.network.extenstions.dataOrError
import javax.inject.Inject

class HighScoresRemoteDataSourceImpl @Inject constructor(
    private val highScoresApi: HighScoresApi,
) : HighScoresRemoteDataSource {

    override suspend fun getHighScores(): Result<List<HighScoresModel>> =
        suspendRunCatching {
            highScoresApi.getHighScores()
        }.mapCatching { response ->
            response.dataOrError {
                it.toDomainModel()
            }
        }
}
