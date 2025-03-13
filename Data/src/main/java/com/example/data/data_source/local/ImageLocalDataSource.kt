package com.example.data.data_source.local

import com.example.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageLocalDataSource {
    fun observeImagesByUserId(userId: String): Flow<List<ImageModel>>
    suspend fun getAllImages(): List<ImageModel>
    suspend fun getImageById(id: Int): ImageModel?
    suspend fun getImagesByUserId(userId: Int): List<ImageModel>
    suspend fun insertImage(image: ImageModel)
    suspend fun updateScore(imageId: Int, score: Int)
    suspend fun deleteImageById(imageId: Int)
    suspend fun deleteAllImagesByUserId(userId: String)
    suspend fun insertImages(images: List<ImageModel>)
    suspend fun getPersonalBestScore(userId: String): ImageModel?
}
