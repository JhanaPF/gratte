package com.example.domain.use_cases

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageByIdUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(imageId: Int): Result<ImageModel?> =
        imageRepository.getImageById(imageId)
}
