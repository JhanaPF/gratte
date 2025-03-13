package com.example.domain.repository

import com.example.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun observeImagesByUserId(userId: String): Flow<Result<List<ImageModel>>>
    suspend fun getAllImages(): List<ImageModel>
    suspend fun getImagesByUserId(userId: Int): List<ImageModel>
    suspend fun insertImage(image: ImageModel)
    suspend fun updateScore(imageId: Int, score: Int)
    suspend fun deleteAllImagesByUserId(userId: String): Result<Unit>
    suspend fun getImageById(pictureId: Int): Result<ImageModel?>
    suspend fun deleteImageById(imageId: Int): Result<Unit>
}
