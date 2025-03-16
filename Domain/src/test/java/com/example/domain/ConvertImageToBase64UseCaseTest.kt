package com.example.domain

import com.example.domain.model.ImageData
import com.example.domain.use_cases.image.ConvertImageToBase64UseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Base64

class ConvertImageToBase64UseCaseTest {

    private val useCase = ConvertImageToBase64UseCase()

    @Test
    fun `invoke returns correct base64 for non-empty data`() = runTest {
        // Given
        val inputString = "hello world"
        val imageData = ImageData(inputString.toByteArray())

        // When
        val result = useCase.invoke(imageData)

        // Then
        val expected = Base64.getEncoder().encodeToString(inputString.toByteArray())
        assertEquals(expected, result)
    }

    @Test
    fun `invoke returns correct base64 for empty data`() = runTest {
        // Given
        val imageData = ImageData(ByteArray(0))

        // When
        val result = useCase.invoke(imageData)

        // Then
        assertEquals("", result)
    }
}
