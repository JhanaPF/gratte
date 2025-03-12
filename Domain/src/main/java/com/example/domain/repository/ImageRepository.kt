package com.example.domain.repository

import com.example.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun observeImagesByUserId(userId: Int): Flow<Result<List<ImageModel>>>
    suspend fun getAllImages(): List<ImageModel>
    suspend fun getImagesByUserId(userId: Int): List<ImageModel>
    suspend fun insertImage(image: ImageModel)
    suspend fun updateScore(imageId: Int, score: Int)
}
