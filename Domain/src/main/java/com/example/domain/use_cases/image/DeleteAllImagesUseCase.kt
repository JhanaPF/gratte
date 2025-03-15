package com.example.domain.use_cases.image

import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.user.GetUserIdUseCase
import javax.inject.Inject

class DeleteAllImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
) {
    suspend operator fun invoke(): Result<Unit> {
        val userId = getUserIdUseCase()
        return imageRepository.deleteAllImagesByUserId(userId)
    }
}
