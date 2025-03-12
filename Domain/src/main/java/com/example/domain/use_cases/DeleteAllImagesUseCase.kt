package com.example.domain.use_cases

import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteAllImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        val userId = getUserIdUseCase()
        imageRepository.deleteAllImagesByUserId(userId)
    }
}
