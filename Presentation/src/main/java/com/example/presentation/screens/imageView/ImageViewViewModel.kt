package com.example.presentation.screens.imageView

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.use_cases.image.ConvertBase64ToBitmapUseCase
import com.example.domain.use_cases.image.DeleteImageByIdUseCase
import com.example.domain.use_cases.image.GetImageByIdUseCase
import com.example.presentation.screens.imageView.navigation.ImageView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val convertBase64ToByteArrayUseCase: ConvertBase64ToBitmapUseCase,
    private val getImageByIdUseCase: GetImageByIdUseCase,
    private val deleteImageByIdUseCase: DeleteImageByIdUseCase,
) : ViewModel() {

    private val imageId = savedStateHandle.toRoute<ImageView>().imageId

    private val _events: Channel<ImageViewEvents> = Channel(Channel.BUFFERED)
    val events: Flow<ImageViewEvents> = _events.receiveAsFlow()

    private val _state = MutableStateFlow<ImageViewUiState>(ImageViewUiState.Loading)
    val state: StateFlow<ImageViewUiState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = runCatching {
                val imageModel = getImageByIdUseCase(imageId).getOrThrow()
                val score = imageModel?.score ?: 0
                val base64Image = imageModel?.image ?: throw IllegalStateException("Image not found")

                val decodedImage = convertBase64ToByteArrayUseCase(base64Image).getOrThrow()

                decodedImage to score
            }

            _state.value = result.fold(
                onSuccess = { (decodedImage, score) ->
                    ImageViewUiState.Success(image = decodedImage, score = score)
                },
                onFailure = { error ->
                    ImageViewUiState.Error(error)
                },
            )
        }
    }

    fun onDeleteClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteImageByIdUseCase(imageId)
                .onSuccess {
                    _events.send(ImageViewEvents.NavigateBack)
                }.onFailure {
                    _state.value = ImageViewUiState.Error(it)
                }
        }
    }
}
