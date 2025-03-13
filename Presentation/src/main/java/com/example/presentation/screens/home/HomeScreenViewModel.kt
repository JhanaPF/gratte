package com.example.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.HighScoresModel
import com.example.domain.model.ImageModel
import com.example.domain.use_cases.GetHighScoresUseCase
import com.example.domain.use_cases.ObservePersonalBestScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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
class HomeScreenViewModel @Inject constructor(
    observePersonalBestScoreUseCase: ObservePersonalBestScoreUseCase,
    private val getHighScoresUseCase: GetHighScoresUseCase,
) : ViewModel() {

    private val highScoresFlow = MutableStateFlow<List<HighScoresModel>>(emptyList())

    private val errorFlow = MutableStateFlow<Throwable?>(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHighScoresUseCase()
                .onSuccess { scores ->
                    highScoresFlow.value = scores
                }
                .onFailure { throwable ->
                    errorFlow.value = throwable
                }
        }
    }

    val state: StateFlow<HomeScreenUiState> =
        combine(
            highScoresFlow,
            observePersonalBestScoreUseCase(),
            errorFlow,
        ) { scores, personalBest, throwable ->
            when {
                throwable != null -> HomeScreenUiState.Error(throwable)
                else -> HomeScreenUiState.Success(
                    highScores = scores.map {
                        it.toHighScoresItem()
                    }.toPersistentList(),
                    personalBest = personalBest?.toHighScoresItem(),
                )
            }
        }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeScreenUiState.Loading,
            )

    private fun HighScoresModel.toHighScoresItem(): HighScoresItem =
        HighScoresItem(
            rank = rank,
            name = userid,
            pictureUrl = pictureUrl,
            score = score,
        )

    private fun ImageModel?.toHighScoresItem(): HighScoresItem? =
        this?.let {
            HighScoresItem(
                rank = "11TH", // With a normal backend i would just fetch it but i wanted to show an observe / combining example
                name = it.userId ?: return null,
                pictureUrl = null,
                score = it.score ?: return null,
            )
        }
}
