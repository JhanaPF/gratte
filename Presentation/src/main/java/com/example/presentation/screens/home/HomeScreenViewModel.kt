package com.example.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.GetHighScoresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getHighScoresUseCase: GetHighScoresUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val state: StateFlow<HomeScreenUiState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHighScoresUseCase()
                .onSuccess {
                    _state.value = HomeScreenUiState.Success(
                        it.map { highScore ->
                            HighScoresItem(
                                rank = highScore.rank,
                                name = highScore.userid,
                                pictureUrl = highScore.pictureUrl,
                                score = highScore.score,
                            )
                        },
                    )
                }
                .onFailure {
                    _state.value = HomeScreenUiState.Error(it)
                }
        }
    }
}
