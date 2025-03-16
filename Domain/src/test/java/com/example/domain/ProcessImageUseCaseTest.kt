package com.example.domain

import com.example.domain.gpui.ImageProcessor
import com.example.domain.model.ImageData
import com.example.domain.use_cases.gpui.ProcessImageUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProcessImageUseCaseTest {

    @MockK
    private lateinit var imageProcessor: ImageProcessor

    @InjectMockKs
    private lateinit var processImageUseCase: ProcessImageUseCase

    @Test
    fun `when applyCrt is false, should only pixelate and return pixelated image`() = runTest {
        // Given
        val inputImage = ImageData("original".toByteArray())
        val pixelatedImage = ImageData("pixelated".toByteArray())
        val params = ProcessImageUseCase.FilterParameters(
            pixelSize = 5f,
            applyCrt = false,
        )

        coEvery { imageProcessor.applyPixelation(inputImage, 5f) } returns pixelatedImage
        coEvery { imageProcessor.applyCrt(any()) } returns ImageData("should not be used".toByteArray())

        // When
        val result = processImageUseCase(inputImage, params)

        // Then
        assertEquals(pixelatedImage, result, "Result should match pixelated image")
        coVerify(exactly = 1) {
            imageProcessor.applyPixelation(inputImage, 5f)
        }
        coVerify(exactly = 0) {
            imageProcessor.applyCrt(any())
        }
    }

    @Test
    fun `when applyCrt is true, should pixelate then apply CRT and return final image`() = runTest {
        // Given
        val inputImage = ImageData("original".toByteArray())
        val pixelatedImage = ImageData("pixelated".toByteArray())
        val crtImage = ImageData("crt".toByteArray())
        val params = ProcessImageUseCase.FilterParameters(
            pixelSize = 3f,
            applyCrt = true,
        )

        coEvery { imageProcessor.applyPixelation(inputImage, 3f) } returns pixelatedImage
        coEvery { imageProcessor.applyCrt(pixelatedImage) } returns crtImage

        // When
        val result = processImageUseCase(inputImage, params)

        // Then
        assertEquals(crtImage, result, "Result should match CRT-processed image")
        coVerify(exactly = 1) { imageProcessor.applyPixelation(inputImage, 3f) }
        coVerify(exactly = 1) { imageProcessor.applyCrt(pixelatedImage) }
    }
}
