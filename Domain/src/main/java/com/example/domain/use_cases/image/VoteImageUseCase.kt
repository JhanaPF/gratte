package com.example.domain.use_cases.image

import com.example.common.extensions.suspendRunCatching
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class VoteImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(voteType: VoteType): Result<Unit> = suspendRunCatching {
        when (voteType) {
            VoteType.POSITIVE -> {
                imageRepository.votePositive()
            }

            VoteType.NEGATIVE -> {
                imageRepository.voteNegative()
            }
        }
    }

    enum class VoteType {
        POSITIVE,
        NEGATIVE,
    }
}
