package com.example.data.data_source.local

import com.example.data.dao.ImageDao
import com.example.data.data_source.mapper.toDomain
import com.example.data.data_source.mapper.toEntity
import com.example.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageLocalDataSourceImpl @Inject constructor(
    private val imageDao: ImageDao,
) : ImageLocalDataSource {
    override suspend fun getAllImages(): List<ImageModel> =
        imageDao.getAll().toDomain() ?: emptyList()

    override suspend fun getImageById(id: Int): ImageModel? = imageDao.getById(id).toDomain()

    override suspend fun getImagesByUserId(userId: Int): List<ImageModel> =
        imageDao.getByUserId(userId).toDomain() ?: emptyList()

    override fun observeImagesByUserId(userId: Int): Flow<List<ImageModel>> =
        imageDao.observeByUserId(userId).map { it.toDomain() ?: emptyList() }

    override suspend fun insertImages(images: List<ImageModel>) =
        imageDao.insertAll(images.toEntity())

    override suspend fun insertImage(image: ImageModel) = imageDao.insert(image.toEntity())

    override suspend fun updateScore(imageId: Int, score: Int) =
        imageDao.updateScoreById(imageId, score)

    override suspend fun deleteImageById(imageId: Int) = imageDao.deleteById(imageId)

    override suspend fun deleteImage(image: ImageModel) = imageDao.delete(image.toEntity())
}