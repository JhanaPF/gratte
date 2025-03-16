package com.example.domain

import com.example.domain.use_cases.image.ConvertBase64ToBitmapUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConvertBase64ToBitmapUseCaseTest {

    private val convertBase64ToBitmapUseCase = ConvertBase64ToBitmapUseCase()

    @Test
    fun `invoke with valid base64 and prefix returns success`() = runTest {
        // "Zm9v" is the Base64 for the ASCII string "foo"
        // The use case should strip "base64," then decode "Zm9v".
        val base64String = "base64,Zm9v"

        val result = convertBase64ToBitmapUseCase(base64String)

        assertTrue(result.isSuccess, "Expected success decoding valid base64 with prefix")
        val decodedBytes = result.getOrNull()
        assertNotNull(decodedBytes)
        assertEquals("foo", decodedBytes?.toString(Charsets.UTF_8))
    }

    @Test
    fun `invoke with valid base64 and no prefix returns success`() = runTest {
        // Just "Zm9v" without "base64," prefix
        val base64String = "Zm9v"

        val result = convertBase64ToBitmapUseCase(base64String)

        assertTrue(result.isSuccess, "Expected success decoding valid base64 without prefix")
        val decodedBytes = result.getOrNull()
        assertNotNull(decodedBytes)
        assertEquals("foo", decodedBytes?.toString(Charsets.UTF_8))
    }

    @Test
    fun `invoke with invalid base64 returns failure`() = runTest {
        // "@" symbols are not valid in standard Base64
        val invalidBase64 = "base64,@@@"

        val result = convertBase64ToBitmapUseCase(invalidBase64)

        assertTrue(result.isFailure, "Expected failure decoding invalid base64")
        val exception = result.exceptionOrNull()

        assertNotNull(exception)

        assertTrue(exception is IllegalArgumentException)
    }
}
