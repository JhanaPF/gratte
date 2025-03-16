package com.example.domain

import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.DeleteAllImagesUseCase
import com.example.domain.use_cases.user.GetUserIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeleteAllImagesUseCaseTest {

    @MockK
    private lateinit var imageRepository: ImageRepository

    @MockK
    private lateinit var getUserIdUseCase: GetUserIdUseCase

    @InjectMockKs
    private lateinit var deleteAllImagesUseCase: DeleteAllImagesUseCase

    @Test
    fun `invoke returns success when repository call succeeds`() = runTest {
        // Given
        val userId = "user123"
        coEvery { getUserIdUseCase() } returns userId
        coEvery { imageRepository.deleteAllImagesByUserId(userId) } returns Result.success(Unit)

        // When
        val result = deleteAllImagesUseCase()

        // Then
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { getUserIdUseCase() }
        coVerify(exactly = 1) { imageRepository.deleteAllImagesByUserId(userId) }
    }

    @Test
    fun `invoke returns failure when repository call fails`() = runTest {
        // Given
        val userId = "user456"
        val errorMsg = "Failed to delete images"
        coEvery { getUserIdUseCase() } returns userId
        coEvery { imageRepository.deleteAllImagesByUserId(userId) } returns Result.failure(Exception(errorMsg))

        // When
        val result = deleteAllImagesUseCase()

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception?.message?.contains(errorMsg) == true)
        coVerify(exactly = 1) { getUserIdUseCase() }
        coVerify(exactly = 1) { imageRepository.deleteAllImagesByUserId(userId) }
    }
}
