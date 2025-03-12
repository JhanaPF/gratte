package com.example.presentation.screens.imageView

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.use_cases.ConvertBase64ToBitmapUseCase
import com.example.domain.use_cases.GetImageByIdUseCase
import com.example.presentation.screens.imageView.navigation.ImageView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val convertBase64ToByteArrayUseCase: ConvertBase64ToBitmapUseCase,
    private val getImageByIdUseCase: GetImageByIdUseCase,
) : ViewModel() {

    private val imageId = savedStateHandle.toRoute<ImageView>().imageId

    private val _state = MutableStateFlow<ImageViewUiState>(ImageViewUiState.Loading)
    val state: StateFlow<ImageViewUiState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val imageModel = getImageByIdUseCase(imageId)
                val image = imageModel?.image?.let { convertBase64ToByteArrayUseCase(it) }
                if (image == null) {
                    throw IllegalStateException("Image not found")
                }
                _state.value = ImageViewUiState.Success(image)
            }.onFailure {
                _state.value = ImageViewUiState.Error(it)
            }
        }
    }

    fun onDeleteClicked() {
    }
}
