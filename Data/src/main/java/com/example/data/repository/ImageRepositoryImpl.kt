package com.example.data.repository

import com.example.common.extensions.suspendRunCatching
import com.example.data.data_source.local.ImageLocalDataSource
import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageLocalDataSource: ImageLocalDataSource,
) : ImageRepository {
    override fun observeImagesByUserId(userId: String) =
        imageLocalDataSource.observeImagesByUserId(userId)
            .map { entities ->
                Result.success(entities)
            }
            .catch { error ->
                emit(Result.failure(error))
            }

    override suspend fun getAllImages() = imageLocalDataSource.getAllImages()
    override suspend fun getImagesByUserId(userId: Int) =
        imageLocalDataSource.getImagesByUserId(userId)

    override suspend fun insertImage(image: ImageModel) = imageLocalDataSource.insertImage(image)
    override suspend fun updateScore(imageId: Int, score: Int) =
        imageLocalDataSource.updateScore(imageId, score)

    override suspend fun deleteAllImagesByUserId(userId: String) = suspendRunCatching {
        imageLocalDataSource.deleteAllImagesByUserId(userId)
    }

    override suspend fun deleteImageById(imageId: Int) = suspendRunCatching {
        imageLocalDataSource.deleteImageById(imageId)
    }

    override suspend fun getImageById(pictureId: Int): Result<ImageModel?> = suspendRunCatching {
        imageLocalDataSource.getImageById(pictureId)
    }
}
