package com.example.domain

import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.VoteImageUseCase
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
class VoteImageUseCaseTest {

    @MockK
    private lateinit var imageRepository: ImageRepository

    @InjectMockKs
    private lateinit var useCase: VoteImageUseCase

    @Test
    fun `votePositive returns success when repository call completes without throwing`() = runTest {
        // Given: repository call completes successfully
        coEvery { imageRepository.votePositive() } returns Unit

        // When
        val result = useCase(VoteImageUseCase.VoteType.POSITIVE)

        // Then
        assertTrue(result.isSuccess, "Result should be Success")
        coVerify(exactly = 1) { imageRepository.votePositive() }
        coVerify(exactly = 0) { imageRepository.voteNegative() }
    }

    @Test
    fun `votePositive returns failure when repository call throws an exception`() = runTest {
        // Given: repository call throws an exception
        coEvery { imageRepository.votePositive() } throws RuntimeException("Positive vote failed")

        // When
        val result = useCase(VoteImageUseCase.VoteType.POSITIVE)

        // Then
        assertTrue(result.isFailure, "Result should be Failure")
        val exception = result.exceptionOrNull()
        assertTrue(exception is RuntimeException)
        assertTrue(exception?.message?.contains("Positive vote failed") == true)
        coVerify(exactly = 1) { imageRepository.votePositive() }
        coVerify(exactly = 0) { imageRepository.voteNegative() }
    }

    @Test
    fun `voteNegative returns success when repository call completes without throwing`() = runTest {
        // Given
        coEvery { imageRepository.voteNegative() } returns Unit

        // When
        val result = useCase(VoteImageUseCase.VoteType.NEGATIVE)

        // Then
        assertTrue(result.isSuccess, "Result should be Success")
        coVerify(exactly = 1) { imageRepository.voteNegative() }
        coVerify(exactly = 0) { imageRepository.votePositive() }
    }

    @Test
    fun `voteNegative returns failure when repository call throws an exception`() = runTest {
        // Given
        coEvery { imageRepository.voteNegative() } throws RuntimeException("Negative vote failed")

        // When
        val result = useCase(VoteImageUseCase.VoteType.NEGATIVE)

        // Then
        assertTrue(result.isFailure, "Result should be Failure")
        val exception = result.exceptionOrNull()
        assertTrue(exception is RuntimeException)
        assertTrue(exception?.message?.contains("Negative vote failed") == true)
        coVerify(exactly = 1) { imageRepository.voteNegative() }
        coVerify(exactly = 0) { imageRepository.votePositive() }
    }
}
