package com.example.domain.use_cases.image

import com.example.domain.model.ImageData
import javax.inject.Inject
import java.util.Base64

class ConvertImageToBase64UseCase @Inject constructor() {
    suspend operator fun invoke(image: ImageData): String {
        return Base64.getEncoder().encodeToString(image.bytes)
    }
}
