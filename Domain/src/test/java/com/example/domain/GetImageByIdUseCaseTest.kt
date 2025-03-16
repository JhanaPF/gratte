package com.example.domain

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.GetImageByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetImageByIdUseCaseTest {

    @MockK
    private lateinit var imageRepository: ImageRepository

    @InjectMockKs
    private lateinit var getImageByIdUseCase: GetImageByIdUseCase

    @Test
    fun `invoke returns success with non-null ImageModel`() = runTest {
        // Given
        val imageId = 123
        val dummyImage = ImageModel(id = imageId, image = "http://example.com/dummy.png")
        coEvery { imageRepository.getImageById(imageId) } returns Result.success(dummyImage)

        // When
        val result = getImageByIdUseCase(imageId)

        // Then
        assertTrue(result.isSuccess)

        val returnedImage = result.getOrNull()
        assertEquals(dummyImage, returnedImage)

        coVerify(exactly = 1) { imageRepository.getImageById(imageId) }
    }

    @Test
    fun `invoke returns success with null image when repository result is success(null)`() = runTest {
        // Given
        val imageId = 456
        coEvery { imageRepository.getImageById(imageId) } returns Result.success(null)

        // When
        val result = getImageByIdUseCase(imageId)

        // Then
        assertTrue(result.isSuccess)
        val returnedImage = result.getOrNull()
        assertEquals(null, returnedImage)

        coVerify(exactly = 1) { imageRepository.getImageById(imageId) }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        // Given
        val imageId = 789
        val errorMessage = "Image not found"
        val exception = IllegalStateException(errorMessage)
        coEvery { imageRepository.getImageById(imageId) } returns Result.failure(exception)

        // When
        val result = getImageByIdUseCase(imageId)

        // Then
        assertTrue(result.isFailure)
        val resultException = result.exceptionOrNull()

        assertTrue(resultException is IllegalStateException)
        assertTrue(resultException?.message?.contains(errorMessage) == true)

        coVerify(exactly = 1) { imageRepository.getImageById(imageId) }
    }
}
