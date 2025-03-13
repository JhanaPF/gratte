package com.example.presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.composables.ErrorView
import com.example.presentation.composables.LottieLoader
import com.example.presentation.composables.ScoreRow
import com.example.presentation.theme.PixeliseItTheme
import com.example.presentation.theme.retro
import com.example.presentation.utils.colorForRank
import com.example.presentation.utils.randomFlashyColor

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PixeliseItTheme {
        HomeContent(
            state = state,
        )
    }
}

@Composable
fun HomeContent(
    state: HomeScreenUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(
                top = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 46.dp),
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = retro,
            fontSize = 24.sp,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 46.dp),
            text = stringResource(id = R.string.home_subtitle),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = retro,
            fontSize = 24.sp,
        )

        when (state) {
            is HomeScreenUiState.Loading -> {
                LoadingIndicator()
            }

            is HomeScreenUiState.Success -> {
                Success(state = state)
            }

            is HomeScreenUiState.Error -> {
                ErrorView(message = state.message)
            }
        }
    }
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        LottieLoader(
            modifier = Modifier.align(Alignment.Center),
            resId = R.raw.loader,
        )
    }
}

@Composable
fun Success(
    modifier: Modifier = Modifier,
    state: HomeScreenUiState.Success,
) {
    Column(
        modifier = modifier,
    ) {
        HighScoresList(
            modifier = Modifier
                .padding(bottom = 24.dp),
            highScores = state.highScores,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            text = "Your Score:",
            color = MaterialTheme.colorScheme.primary,
            fontFamily = retro,
            fontSize = 24.sp,
        )
        if (state.personalBest != null) {
            PersonalBestScore(
                modifier = Modifier
                    .padding(bottom = 24.dp),
                personalBest = state.personalBest,
            )
        }
    }
}

@Composable
fun PersonalBestScore(
    modifier: Modifier,
    personalBest: HighScoresItem,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = 16.dp)
            .border(2.dp, randomFlashyColor()),
    ) {
        ScoreRow(
            rank = personalBest.rank,
            score = personalBest.score.toString(),
            name = personalBest.name,
            color = Color.Green,
        )
    }
}

@Composable
fun HighScoresList(
    modifier: Modifier = Modifier,
    highScores: List<HighScoresItem>,
) {
    LazyColumn(modifier = modifier) {
        item {
            ScoreRow(
                rank = stringResource(R.string.first_row),
                score = stringResource(R.string.second_row),
                name = stringResource(R.string.third_row),
                color = Color.Green,
            )
        }
        itemsIndexed(highScores) { index, item ->
            ScoreRow(
                rank = item.rank,
                score = item.score.toString(),
                name = item.name,
                color = colorForRank(index),
            )
        }
    }
}
