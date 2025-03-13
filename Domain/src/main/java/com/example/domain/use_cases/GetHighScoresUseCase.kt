package com.example.domain.use_cases

import com.example.domain.model.HighScoresModel
import com.example.domain.repository.HighScoresRepository
import javax.inject.Inject

class GetHighScoresUseCase @Inject constructor(
    private val highScoresRepository: HighScoresRepository,
) {
    suspend operator fun invoke(): Result<List<HighScoresModel>> =
        highScoresRepository.getHighScores()
}
