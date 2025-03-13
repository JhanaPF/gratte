package com.example.domain.use_cases

import com.example.domain.model.HighScoresModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class GetHighScoresUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(): Result<List<HighScoresModel>> =
        imageRepository.getHighScores()
}
