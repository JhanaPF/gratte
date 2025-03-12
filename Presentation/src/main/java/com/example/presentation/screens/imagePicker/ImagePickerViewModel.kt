package com.example.presentation.screens.imagePicker

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ImageModel
import com.example.domain.use_cases.FilterParameters
import com.example.domain.use_cases.ProcessImageUseCase
import com.example.domain.use_cases.SendUserPictureUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    val processImageUseCase: ProcessImageUseCase,
    val sendUserPictureUserCase: SendUserPictureUserCase,
) : ViewModel() {

    private val initialState: ImagePickerUiState =
        ImagePickerUiState(
            image = null,
        )

    private val originalImageFlow: MutableStateFlow<Bitmap?> =
        MutableStateFlow(null)

    private val isLoadingFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val filterParamsFlow = MutableStateFlow(FilterParameters())

    val state: StateFlow<ImagePickerUiState> =
        combine(
            originalImageFlow,
            filterParamsFlow,
            isLoadingFlow,
        ) { image, params, isLoading ->
            ImagePickerUiState(
                image = image?.let { processImageUseCase(it, params) },
                isLoading = isLoading
            )
        }.flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = initialState,
            )

    fun onSendImageClicked() {
        state.value.image?.let { image ->
            viewModelScope.launch {
                isLoadingFlow.value = true
                sendUserPictureUserCase(image)
                    .onSuccess {
                        originalImageFlow.value = null
                    }
                isLoadingFlow.value = false
            }
        }
    }

    fun onSelectImage(image: Bitmap) {
        originalImageFlow.value = image
    }

    fun onPixelSizeChanged(pixelSize: Float) {
        filterParamsFlow.value = filterParamsFlow.value.copy(pixelSize = pixelSize)
    }

    fun onCrtToggled(enabled: Boolean) {
        filterParamsFlow.value = filterParamsFlow.value.copy(applyCrt = enabled)
    }

    fun onCloseImageClick() {
        originalImageFlow.value = null
    }
}