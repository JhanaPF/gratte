package com.example.presentation.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.ConvertBase64ToBitmapUseCase
import com.example.domain.use_cases.ObserveUserPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val convertBase64ToBitmapUseCase: ConvertBase64ToBitmapUseCase,
    private val observeUserPicturesUseCase: ObserveUserPicturesUseCase,
) : ViewModel() {

    private val _events: Channel<GalleryEvents> = Channel(Channel.BUFFERED)
    val events: Flow<GalleryEvents> = _events.receiveAsFlow()

    val state: StateFlow<GalleryUiState> =
        observeUserPicturesUseCase()
            .distinctUntilChanged()
            .map { pictures ->
                pictures.fold(
                    onSuccess = { images ->
                        GalleryUiState.Success(
                            pictures = images
                                .map { imageModel ->
                                    convertBase64ToBitmapUseCase(imageModel.image)
                                }
                                .toPersistentList(),
                        )
                    },
                    onFailure = { error ->
                        GalleryUiState.Error(error)
                    },
                )
            }.flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = GalleryUiState.Loading,
            )

    fun onImageClick() {
    }
}
