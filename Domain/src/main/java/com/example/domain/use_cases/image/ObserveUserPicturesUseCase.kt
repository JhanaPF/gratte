package com.example.domain.use_cases.image

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.user.GetUserIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserveUserPicturesUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
) {
    operator fun invoke(): Flow<Result<List<ImageModel>>> = flow {
        val userId = getUserIdUseCase() // suspend call
        imageRepository.observeImagesByUserId(userId).collect { result ->
            emit(result)
        }
    }
}
