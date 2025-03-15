package com.example.domain.use_cases.image

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class GetDailyVoteUsersImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(): Result<List<ImageModel>> =
        imageRepository.getDailyVoteUsersImages()
}
