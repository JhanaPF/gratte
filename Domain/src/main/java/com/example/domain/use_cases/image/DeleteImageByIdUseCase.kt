package com.example.domain.use_cases.image

import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteImageByIdUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(imageId: Int): Result<Unit> =
        imageRepository.deleteImageById(imageId)
}
