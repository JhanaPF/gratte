package com.example.domain.use_cases

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class SendUserPictureUserCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
) {
    suspend operator fun invoke(image: ImageModel): Result<Unit> = runCatching {
        val userId = getUserIdUseCase()
        imageRepository.insertImage(image.copy(userId = userId))
    }
}