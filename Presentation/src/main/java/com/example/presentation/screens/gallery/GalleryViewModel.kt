package com.example.presentation.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor() : ViewModel() {

    private val isLoadingFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val _events: Channel<GalleryEvents> = Channel(Channel.BUFFERED)
    val events: Flow<GalleryEvents> = _events.receiveAsFlow()

    val state: StateFlow<GalleryUiState> =
        combine(
            isLoadingFlow,
        ) { isLoading ->
            GalleryUiState.Error
        }.flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = GalleryUiState.Loading,
            )

    fun onImageClick() {
    }
}
