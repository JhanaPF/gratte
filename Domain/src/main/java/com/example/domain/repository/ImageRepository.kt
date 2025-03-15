package com.example.domain.repository

import com.example.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun observeImagesByUserId(userId: String): Flow<Result<List<ImageModel>>>
    suspend fun sendImage(image: ImageModel): Result<Unit>
    suspend fun updateScore(imageId: Int, score: Int)
    suspend fun deleteAllImagesByUserId(userId: String): Result<Unit>
    suspend fun getImageById(pictureId: Int): Result<ImageModel?>
    suspend fun deleteImageById(imageId: Int): Result<Unit>
    suspend fun observePersonalBestScore(userId: String): Flow<ImageModel?>
    suspend fun getDailyVoteUsersImages(): Result<List<ImageModel>>
    suspend fun votePositive()
    suspend fun voteNegative()
}
