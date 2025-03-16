package com.example.domain

import app.cash.turbine.test
import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.ObserveUserPicturesUseCase
import com.example.domain.use_cases.user.GetUserIdUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ObserveUserPicturesUseCaseTest {

    @MockK
    private lateinit var getUserIdUseCase: GetUserIdUseCase

    @MockK
    private lateinit var imageRepository: ImageRepository

    @InjectMockKs
    private lateinit var observeUserPicturesUseCase: ObserveUserPicturesUseCase

    @Test
    fun `given valid user id and repository emits success then use case emits success`() = runTest {
        // Given
        val userId = "user123"
        val dummyImages = listOf(
            ImageModel(image = "dummyBase64", id = 1, userId = userId, score = 100),
            ImageModel(image = "dummyBase64_2", id = 2, userId = userId, score = 200),
        )
        val successResult = Result.success(dummyImages)
        coEvery { getUserIdUseCase() } returns userId
        every { imageRepository.observeImagesByUserId(userId) } returns flowOf(successResult)

        // When & Then
        observeUserPicturesUseCase().test {
            val emission = awaitItem()
            assertTrue(emission.isSuccess)
            assertEquals(successResult, emission)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given valid user id and repository emits failure then use case emits failure`() = runTest {
        // Given
        val userId = "user123"
        val failureResult = Result.failure<List<ImageModel>>(RuntimeException("Error fetching images"))
        coEvery { getUserIdUseCase() } returns userId
        every { imageRepository.observeImagesByUserId(userId) } returns flowOf(failureResult)

        // When & Then
        observeUserPicturesUseCase().test {
            val emission = awaitItem()
            assertTrue(emission.isFailure)
            val exception = emission.exceptionOrNull()
            assertEquals("Error fetching images", exception?.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
