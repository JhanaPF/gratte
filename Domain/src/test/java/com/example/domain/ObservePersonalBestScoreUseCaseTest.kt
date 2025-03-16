package com.example.domain

import app.cash.turbine.test
import com.example.domain.model.ImageModel
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.highscore.ObservePersonalBestScoreUseCase
import com.example.domain.use_cases.user.GetUserIdUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ObservePersonalBestScoreUseCaseTest {

    @MockK
    private lateinit var getUserIdUseCase: GetUserIdUseCase

    @MockK
    private lateinit var imageRepository: ImageRepository

    @InjectMockKs
    private lateinit var observePersonalBestScoreUseCase: ObservePersonalBestScoreUseCase

    @Test
    fun `invoke collects multiple emissions from repository flow`() = runTest {
        // Given
        val userId = "user123"
        coEvery { getUserIdUseCase() } returns userId

        val image1 = ImageModel(id = 1, image = "http://example.com/image1.png")
        val image2 = ImageModel(id = 2, image = "http://example.com/image2.png")

        coEvery {
            imageRepository.observePersonalBestScore(userId)
        } returns flowOf(image1, image2)

        // Then
        observePersonalBestScoreUseCase().test {
            val emission1 = awaitItem()
            assertEquals(image1, emission1)

            val emission2 = awaitItem()
            assertEquals(image2, emission2)

            awaitComplete()
        }
    }

    @Test
    fun `invoke emits null successfully`() = runTest {
        // Given
        val userId = "user123"
        coEvery { getUserIdUseCase() } returns userId

        coEvery {
            imageRepository.observePersonalBestScore(userId)
        } returns flowOf(null)

        // Then
        observePersonalBestScoreUseCase().test {
            val emission = awaitItem()
            assertEquals(null, emission)
            awaitComplete()
        }
    }
}
