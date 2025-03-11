package com.example.data.repository

import com.example.data.data_source.local.ImageLocalDataSource
import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageLocalDataSource: ImageLocalDataSource
) : ImageRepository {
    override fun observeImagesByUserId(userId: Int) =
        imageLocalDataSource.observeImagesByUserId(userId)

    override suspend fun getAllImages() = imageLocalDataSource.getAllImages()
    override suspend fun getImagesByUserId(userId: Int) =
        imageLocalDataSource.getImagesByUserId(userId)

    override suspend fun insertImage(image: ImageModel) = imageLocalDataSource.insertImage(image)
    override suspend fun updateScore(imageId: Int, score: Int) =
        imageLocalDataSource.updateScore(imageId, score)
}