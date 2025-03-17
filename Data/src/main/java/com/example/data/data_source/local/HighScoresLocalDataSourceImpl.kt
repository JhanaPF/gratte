package com.example.data.data_source.local

import com.example.persistence.dao.HighScoresDao
import com.example.data.data_source.mapper.toDomain
import com.example.data.data_source.mapper.toEntity
import com.example.domain.model.HighScoresModel
import javax.inject.Inject

class HighScoresLocalDataSourceImpl @Inject constructor(
    private val highScoresDao: HighScoresDao,
) : HighScoresLocalDataSource {

    override suspend fun getHighScores(): List<HighScoresModel>? =
        highScoresDao.getAll().toDomain()

    override suspend fun insertHighScores(highScores: List<HighScoresModel>) {
        highScoresDao.insertAll(highScores.toEntity())
    }
}
