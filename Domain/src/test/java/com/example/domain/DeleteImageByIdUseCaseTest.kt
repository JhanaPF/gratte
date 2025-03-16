package com.example.domain

import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.DeleteImageByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeleteImageByIdUseCaseTest {

    @MockK
    private lateinit var imageRepository: ImageRepository

    @InjectMockKs
    private lateinit var deleteImageByIdUseCase: DeleteImageByIdUseCase

    @Test
    fun `invoke returns success when repository call return success`() = runTest {
        // Given
        val imageId = 42
        coEvery { imageRepository.deleteImageById(imageId) } returns Result.success(Unit)

        // When
        val result = deleteImageByIdUseCase(imageId)

        // Then
        assertTrue(result.isSuccess, "Result should be Success")
        coVerify(exactly = 1) { imageRepository.deleteImageById(imageId) }
    }

    @Test
    fun `invoke returns failure when repository return failure`() = runTest {
        // Given
        val imageId = 99
        coEvery { imageRepository.deleteImageById(imageId) } returns Result.failure(RuntimeException("Delete failed"))

        // When
        val result = deleteImageByIdUseCase(imageId)

        // Then
        assertTrue(result.isFailure, "Delete failed")
        val exception = result.exceptionOrNull()
        assertTrue(exception is RuntimeException)
        assertTrue(exception?.message?.contains("Delete failed") == true)

        coVerify(exactly = 1) { imageRepository.deleteImageById(imageId) }
    }
}
