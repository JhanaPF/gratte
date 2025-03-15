package com.example.domain.use_cases.highscore

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.user.GetUserIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObservePersonalBestScoreUseCase @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(): Flow<ImageModel?> = flow {
        val userId = getUserIdUseCase() // suspend call
        imageRepository.observePersonalBestScore(userId).collect { result ->
            emit(result)
        }
    }
}
