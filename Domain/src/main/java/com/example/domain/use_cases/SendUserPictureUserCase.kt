package com.example.domain.use_cases

import android.graphics.Bitmap
import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class SendUserPictureUserCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val convertImageToBase64UseCase: ConvertImageToBase64UseCase,
) {
    suspend operator fun invoke(image: Bitmap): Result<Unit> = runCatching {
        val userId = getUserIdUseCase()
        val base64Image = convertImageToBase64UseCase(image)
        imageRepository.insertImage(ImageModel(userId = userId, image = base64Image))
    }
}
