package com.example.data.data_source.local

import com.example.data.data_source.mapper.toDomain
import com.example.data.data_source.mapper.toEntity
import com.example.domain.model.ImageModel
import com.example.persistence.dao.ImageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageLocalDataSourceImpl @Inject constructor(
    private val imageDao: ImageDao,
) : ImageLocalDataSource {

    override suspend fun getImageById(id: Int): ImageModel? = imageDao.getById(id).toDomain()

    override fun observeImagesByUserId(userId: String): Flow<List<ImageModel>> =
        imageDao.observeByUserId(userId).map { it.toDomain() ?: emptyList() }

    override suspend fun observePersonalScoreBestScore(userId: String): Flow<ImageModel?> =
        imageDao.observePersonalBestScore(userId).map {
            it?.toDomain()
        }

    override suspend fun insertImage(image: ImageModel) = imageDao.insert(image.toEntity())

    override suspend fun updateScore(imageId: Int, score: Int) =
        imageDao.updateScoreById(imageId, score)

    override suspend fun deleteImageById(imageId: Int) = imageDao.deleteById(imageId)

    override suspend fun deleteAllImagesByUserId(userId: String) =
        imageDao.deleteAllByUserId(userId)
}
