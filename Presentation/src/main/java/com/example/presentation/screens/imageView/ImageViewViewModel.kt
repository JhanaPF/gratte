package com.example.presentation.screens.imageView

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
class ImageViewViewModel @Inject constructor() : ViewModel() {

    private val isLoadingFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val _events: Channel<ImageViewEvents> = Channel(Channel.BUFFERED)
    val events: Flow<ImageViewEvents> = _events.receiveAsFlow()

    val state: StateFlow<ImageViewUiState> =
        combine(
            isLoadingFlow,
        ) { isLoading ->
            ImageViewUiState.Loading
        }.flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ImageViewUiState.Loading,
            )

    fun onDeleteClicked() {
    }
}
