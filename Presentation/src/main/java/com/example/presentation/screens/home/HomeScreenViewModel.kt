package com.example.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.GetHighScoresUseCase
import com.example.domain.use_cases.GetPersonalBestScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getHighScoresUseCase: GetHighScoresUseCase,
    private val getPersonalBestUseCase: GetPersonalBestScoreUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val state: StateFlow<HomeScreenUiState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHighScoresUseCase()
                .onSuccess {
                    _state.value = HomeScreenUiState.Success(
                        highScores = it.map { highScore ->
                            HighScoresItem(
                                rank = highScore.rank,
                                name = highScore.userid,
                                pictureUrl = highScore.pictureUrl,
                                score = highScore.score,
                            )
                        }.toPersistentList(),
                        personalBest = getPersonalBestUseCase()
                            .getOrNull()?.let { personalBest ->
                                HighScoresItem(
                                    rank = "3RD",
                                    name = personalBest.userId ?: "You",
                                    pictureUrl = null,
                                    score = personalBest.score ?: 0,
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
