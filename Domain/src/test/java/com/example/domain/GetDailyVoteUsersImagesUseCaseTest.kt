package com.example.domain

import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.GetDailyVoteUsersImagesUseCase
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
class GetDailyVoteUsersImagesUseCaseTest {

    @MockK
    private lateinit var imageRepository: ImageRepository

    @InjectMockKs
    private lateinit var getDailyVoteUsersImagesUseCase: GetDailyVoteUsersImagesUseCase

    @Test
    fun `invoke returns success with non-empty image list`() = runTest {
        // Given
        val dummyList = listOf(
            ImageModel(id = 1, image = "http://example.com/image1.png"),
            ImageModel(id = 2, image = "http://example.com/image2.png"),
        )
        coEvery { imageRepository.getDailyVoteUsersImages() } returns Result.success(dummyList)

        // When
        val result = getDailyVoteUsersImagesUseCase()

        // Then
        assertTrue(result.isSuccess)
        val images = result.getOrNull()
        assertEquals(dummyList, images, "Returned list should match the dummy data")

        coVerify(exactly = 1) { imageRepository.getDailyVoteUsersImages() }
    }

    @Test
    fun `invoke returns success with empty list`() = runTest {
        // Given
        coEvery { imageRepository.getDailyVoteUsersImages() } returns Result.success(emptyList())

        // When
        val result = getDailyVoteUsersImagesUseCase()

        // Then
        assertTrue(result.isSuccess)
        val images = result.getOrNull()
        assertTrue(images?.isEmpty() == true, "Returned list should be empty")

        coVerify(exactly = 1) { imageRepository.getDailyVoteUsersImages() }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        // Given
        val errorMessage = "Unable to fetch daily vote images"
        val exception = Exception(errorMessage)
        coEvery { imageRepository.getDailyVoteUsersImages() } returns Result.failure(exception)

        // When
        val result = getDailyVoteUsersImagesUseCase()

        // Then
        assertTrue(result.isFailure)
        val thrown = result.exceptionOrNull()
        assertTrue(thrown is Exception)
        assertTrue(thrown?.message?.contains(errorMessage) == true)

        coVerify(exactly = 1) { imageRepository.getDailyVoteUsersImages() }
    }
}
