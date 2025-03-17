package com.example.presentation.screens.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ImageModel
import com.example.domain.use_cases.image.GetDailyVoteUsersImagesUseCase
import com.example.domain.use_cases.image.VoteImageUseCase
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
class ImageVoteScreenViewModel @Inject constructor(
    private val getDailyVoteUsersImagesUseCase: GetDailyVoteUsersImagesUseCase,
    private val voteImageUseCase: VoteImageUseCase,
) : ViewModel() {

    private val voteProfiles = MutableStateFlow<List<ImageModel>>(emptyList())

    private val errorFlow = MutableStateFlow<Throwable?>(null)

    private val endOfProfilesFlow = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            getDailyVoteUsersImagesUseCase()
                .onSuccess { scores ->
                    voteProfiles.value = scores
                }
                .onFailure { throwable ->
                    errorFlow.value = throwable
                }
        }
    }

    val state: StateFlow<ImageVoteScreenUiState> =
        combine(
            voteProfiles,
            endOfProfilesFlow,
            errorFlow,
        ) { profiles, endOfProfiles, throwable ->
            when {
                throwable != null -> ImageVoteScreenUiState.Error(throwable)

                endOfProfiles || profiles.isEmpty() -> ImageVoteScreenUiState.EndOfProfiles

                else -> ImageVoteScreenUiState.Success(
                    pagerItems = profiles.map {
                        PagerItem(
                            name = it.userId ?: "",
                            pictureUrl = it.image,
                            score = it.score ?: 0,
                        )
                    }.toPersistentList(),
                    onLastProfile = ::onLastProfile, // explained in the UIState
                    onVotePositive = ::votePositive, // its another way of handling click than passing trough lambda
                    onVoteNegative = ::voteNegative,
                )
            }
        }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ImageVoteScreenUiState.Loading,
            )

    fun onLastProfile() {
        endOfProfilesFlow.value = true
    }

    fun votePositive() {
        viewModelScope.launch {
            voteImageUseCase(VoteImageUseCase.VoteType.POSITIVE)
        }
    }

    fun voteNegative() {
        viewModelScope.launch {
            voteImageUseCase(VoteImageUseCase.VoteType.NEGATIVE)
        }
    }
}
