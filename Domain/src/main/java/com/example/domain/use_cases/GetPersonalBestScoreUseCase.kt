package com.example.domain.use_cases

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class GetPersonalBestScoreUseCase @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(): Result<ImageModel?> {
        val userId = getUserIdUseCase()
        return imageRepository.getPersonalBestScore(userId)
    }
}
