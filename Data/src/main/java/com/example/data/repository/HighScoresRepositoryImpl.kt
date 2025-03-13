package com.example.data.repository

import com.example.data.data_source.local.HighScoresLocalDataSource
import com.example.data.data_source.remote.HighScoresRemoteDataSourceImpl
import com.example.domain.model.HighScoresModel
import com.example.domain.repository.HighScoresRepository
import javax.inject.Inject

class HighScoresRepositoryImpl @Inject constructor(
    private val highScoresLocalDataSource: HighScoresLocalDataSource,
    private val highScoresRemoteDataSource: HighScoresRemoteDataSourceImpl,
) : HighScoresRepository {

    private var hasFetchedHighScores = false

    override suspend fun insertHighScores(highScores: List<HighScoresModel>) {
        highScoresLocalDataSource.insertHighScores(highScores)
    }

    override suspend fun getHighScores(): Result<List<HighScoresModel>> {
        if (hasFetchedHighScores) {
            highScoresLocalDataSource.getHighScores()?.let { highScores ->
                if (highScores.isNotEmpty()) {
                    return Result.success(highScores)
                }
            }
        }

        highScoresRemoteDataSource.getHighScores()
            .onSuccess {
                highScoresLocalDataSource.insertHighScores(it)
                hasFetchedHighScores = true
            }
        return highScoresLocalDataSource.getHighScores()?.let {
            Result.success(it)
        } ?: Result.failure(Exception("No high scores found"))
    }
}
