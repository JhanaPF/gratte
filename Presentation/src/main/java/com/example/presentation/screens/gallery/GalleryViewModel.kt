package com.example.presentation.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.ConvertBase64ToBitmapUseCase
import com.example.domain.use_cases.DeleteAllImagesUseCase
import com.example.domain.use_cases.ObserveUserPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    observeUserPicturesUseCase: ObserveUserPicturesUseCase,
    private val convertBase64ToByteArrayUseCase: ConvertBase64ToBitmapUseCase,
    private val deleteAllImagesUseCase: DeleteAllImagesUseCase,
) : ViewModel() {

    val state: StateFlow<GalleryUiState> =
        observeUserPicturesUseCase()
            .distinctUntilChanged()
            .map { pictures ->
                pictures.fold(
                    onSuccess = { images ->
                        val pictureList = images.mapNotNull { imageModel ->
                            imageModel.id?.let { id ->
                                PictureItem(
                                    id = id,
                                    imageArray = convertBase64ToByteArrayUseCase(imageModel.image),
                                    score = imageModel.score ?: 0,
                                )
                            }
                        }.toPersistentList()
                        if (pictureList.isEmpty()) {
                            GalleryUiState.Empty
                        } else {
                            GalleryUiState.Success(pictures = pictureList)
                        }
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

    fun onDeleteClick() {
        viewModelScope.launch {
            deleteAllImagesUseCase()
        }
    }
}
