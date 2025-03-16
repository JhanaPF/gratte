package com.example.domain

import com.example.domain.model.HighScoresModel
import com.example.domain.repository.HighScoresRepository
import com.example.domain.use_cases.highscore.GetHighScoresUseCase
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
class GetHighScoresUseCaseTest {

    @MockK
    private lateinit var highScoresRepository: HighScoresRepository

    @InjectMockKs
    private lateinit var getHighScoresUseCase: GetHighScoresUseCase

    @Test
    fun `invoke returns success with list of high scores`() = runTest {
        // Given
        val dummyList = listOf(
            HighScoresModel(id = "1", rank = "1", score = 100, userid = "Alice", pictureUrl = "http://example.com/alice.png"),
            HighScoresModel(id = "2", rank = "2", score = 90, userid = "Bob", pictureUrl = "http://example.com/bob.png"),
        )
        coEvery { highScoresRepository.getHighScores() } returns Result.success(dummyList)

        // When
        val result = getHighScoresUseCase()

        // Then
        assertTrue(result.isSuccess)
        val scores = result.getOrNull()
        assertEquals(dummyList, scores)
        coVerify(exactly = 1) { highScoresRepository.getHighScores() }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        // Given
        val errorMessage = "Failed to fetch scores"
        val exception = Exception(errorMessage)
        coEvery { highScoresRepository.getHighScores() } returns Result.failure(exception)

        // When
        val result = getHighScoresUseCase()

        // Then
        assertTrue(result.isFailure)
        val thrown = result.exceptionOrNull()
        assertTrue(thrown is Exception)
        assertTrue(thrown?.message?.contains(errorMessage) == true)
        coVerify(exactly = 1) { highScoresRepository.getHighScores() }
    }
}
