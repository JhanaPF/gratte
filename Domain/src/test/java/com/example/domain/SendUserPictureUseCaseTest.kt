package com.example.domain

import com.example.domain.model.ImageData
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.ConvertImageToBase64UseCase
import com.example.domain.use_cases.image.SendUserPictureUseCase
import com.example.domain.use_cases.user.GetUserIdUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SendUserPictureUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var getUserIdUseCase: GetUserIdUseCase

    @MockK(relaxed = true)
    private lateinit var imageRepository: ImageRepository

    // Use a relaxed mock for the converter.
    @MockK(relaxed = true)
    private lateinit var convertImageToBase64UseCase: ConvertImageToBase64UseCase

    @InjectMockKs
    private lateinit var useCase: SendUserPictureUseCase

    @Test
    fun `invoke returns success when repository returns success`() = runTest {
        val dummyImageData = ImageData("dummy".toByteArray())
        val userId = "user123"
        val base64Image = "ZHVtbXk="

        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { convertImageToBase64UseCase.invoke(dummyImageData) } returns base64Image
        coEvery { imageRepository.sendImage(any()) } returns Result.success(Unit)

        // When
        val result = useCase(dummyImageData)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `invoke returns failure when repository returns failure`() = runTest {
        // Given
        val dummyImageData = ImageData("dummy".toByteArray())
        val userId = "user123"
        val base64Image = "ZHVtbXk="

        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { convertImageToBase64UseCase.invoke(dummyImageData) } returns base64Image
        val errorMessage = "Error sending image"
        coEvery { imageRepository.sendImage(any()) } returns Result.failure(Exception(errorMessage))

        // When
        val result = useCase(dummyImageData)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains(errorMessage) == true)
    }

    @Test
    fun `invoke returns failure when getUserUseCase throws exception`() = runTest {
        // Given
        val dummyImageData = ImageData("dummy".toByteArray())
        val errorMessage = "User not found"
        coEvery { getUserIdUseCase.invoke() } throws Exception(errorMessage)
        // convertImageToBase64UseCase and imageRepository.sendImage are not expected to be called

        // When
        val result = useCase(dummyImageData)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains(errorMessage) == true)
    }

    @Test
    fun `invoke returns failure when convertImageToBase64UseCase throws exception`() = runTest {
        // Given
        val dummyImageData = ImageData("dummy".toByteArray())
        val userId = "user123"
        val errorMessage = "Conversion failed"
        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { convertImageToBase64UseCase.invoke(dummyImageData) } throws Exception(errorMessage)

        // When
        val result = useCase(dummyImageData)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains(errorMessage) == true)
    }
}
