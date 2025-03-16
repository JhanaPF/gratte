package com.example.domain.use_cases.image

import com.example.common.extensions.suspendRunCatching
import com.example.domain.model.ImageData
import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.user.GetUserIdUseCase
import javax.inject.Inject
import kotlin.random.Random

class SendUserPictureUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val convertImageToBase64UseCase: ConvertImageToBase64UseCase,
) {
    suspend operator fun invoke(image: ImageData): Result<Unit> = suspendRunCatching {
        val userId = getUserIdUseCase()
        val base64Image = convertImageToBase64UseCase(image)
        imageRepository.sendImage(
            ImageModel(
                userId = userId,
                image = base64Image,
                // Since its complicated to do it with mock lets just put a random score here
                score = Random.nextInt(0, 8000),
            ),
        ).getOrThrow()
    }
}
