package com.example.domain

import android.graphics.Bitmap
import com.example.domain.repository.ImageRepository
import com.example.domain.use_cases.image.ConvertBitmapToBase64UseCase
import com.example.domain.use_cases.image.SendUserPictureUseCase
import com.example.domain.use_cases.user.GetUserIdUseCase
import com.example.domain.utils.BitmapConverter
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SendUserPictureUseCaseTest {

    private val getUserIdUseCase = mockk<GetUserIdUseCase>(relaxed = true)
    private val bitmapConverter = mockk<BitmapConverter>(relaxed = true)
    private val imageRepository = mockk<ImageRepository>(relaxed = true)

    // Manually instantiate the use case, using our mocks
    private val useCase = SendUserPictureUseCase(
        imageRepository = imageRepository,
        getUserIdUseCase = getUserIdUseCase,
        convertBitmapToBase64UseCase = ConvertBitmapToBase64UseCase(bitmapConverter),
    )

    @Before
    fun setUp() {
        // Mock static methods of Bitmap so that Bitmap.createBitmap() doesn't crash.
        mockkStatic(Bitmap::class)
        // Return a dummy 1x1 bitmap whenever Bitmap.createBitmap is called.
        io.mockk.every { Bitmap.createBitmap(any<Int>(), any<Int>(), any()) } returns
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke returns success when repository returns success`() = runTest {
        // Given
        val dummyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val userId = "user123"
        val base64Image = "dummyBase64"
        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { bitmapConverter.convert(dummyBitmap) } returns base64Image
        coEvery { imageRepository.sendImage(any()) } returns Result.success(Unit)

        // When
        val result = useCase(dummyBitmap)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `invoke returns failure when repository returns failure`() = runTest {
        // Given
        val dummyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val userId = "user123"
        val base64Image = "dummyBase64"
        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { bitmapConverter.convert(dummyBitmap) } returns base64Image
        val errorMessage = "Error sending image"
        coEvery { imageRepository.sendImage(any()) } returns Result.failure(Exception(errorMessage))

        // When
        val result = useCase(dummyBitmap)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains(errorMessage) == true)
    }

    @Test
    fun `invoke returns failure when getUserUseCase throws exception`() = runTest {
        // Given
        val dummyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val errorMessage = "User not found"
        coEvery { getUserIdUseCase.invoke() } throws Exception(errorMessage)
        // bitmapConverter.convert and imageRepository.sendImage are not expected to be called

        // When
        val result = useCase(dummyBitmap)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains(errorMessage) == true)
    }

    @Test
    fun `invoke returns failure when bitmapConverter throws exception`() = runTest {
        // Given
        val dummyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val userId = "user123"
        val errorMessage = "Conversion failed"
        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { bitmapConverter.convert(dummyBitmap) } throws Exception(errorMessage)

        // When
        val result = useCase(dummyBitmap)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains(errorMessage) == true)
    }
}
