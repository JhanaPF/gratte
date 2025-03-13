package com.example.domain.use_cases

import android.graphics.Bitmap
import com.example.common.extensions.suspendRunCatching
import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class SendUserPictureUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val convertBitmapToBase64UseCase: ConvertBitmapToBase64UseCase,
) {
    suspend operator fun invoke(image: Bitmap): Result<Unit> = suspendRunCatching {
        val userId = getUserIdUseCase()
        val base64Image = convertBitmapToBase64UseCase(image)
        imageRepository.insertImage(ImageModel(userId = userId, image = base64Image))
    }
}
