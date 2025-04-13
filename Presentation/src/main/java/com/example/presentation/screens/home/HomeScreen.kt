package com.example.presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.composables.ErrorContent
import com.example.presentation.composables.LoadingIndicator
import com.example.presentation.composables.ScoreRow
import com.example.presentation.theme.PixeliseItTheme
import com.example.presentation.theme.Red
import com.example.presentation.theme.flamenco
import com.example.presentation.utils.colorForRank
import com.example.presentation.utils.randomFlashyColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import androidx.compose.ui.text.font.FontWeight

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
            text = stringResource(id = R.string.home_title),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = flamenco,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 46.dp),
            text = stringResource(id = R.string.home_subtitle),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = flamenco,
            fontSize = 24.sp,
        )

        when (state) {
            is HomeScreenUiState.Loading -> {
                LoadingIndicator()
            }

            is HomeScreenUiState.Success -> {
                HighScoresList(state = state)
            }

            is HomeScreenUiState.Error -> {
                ErrorContent(throwable = state.message)
            }
        }
    }
}

@Composable
fun HighScoresList(
    state: HomeScreenUiState.Success,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        HighScoresList(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            highScores = state.highScores,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(0.1f),
            text = stringResource(R.string.personnal_score_label),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = flamenco,
            fontSize = 36.sp,
        )
        if (state.personalBest != null) {
            PersonalBestScore(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .weight(0.2f),
                personalBest = state.personalBest,
            )
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.2f),
                text = stringResource(R.string.no_personal_score),
                color = Red,
                fontFamily = flamenco,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun PersonalBestScore(
    personalBest: HighScoresItem,
    modifier: Modifier = Modifier,
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
    highScores: PersistentList<HighScoresItem>,
    modifier: Modifier = Modifier,
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

@Preview(showBackground = true, name = "HomeScreen - Success")
@Composable
private fun HomeScreenSuccessPreview() {
    val dummyHighScores = persistentListOf(
        HighScoresItem(
            rank = "1",
            score = 1500,
            name = "Alice",
            pictureUrl = "https://i.pravatar.cc/150?img=1",
        ),
        HighScoresItem(
            rank = "2",
            score = 2000,
            name = "Bob",
            pictureUrl = "https://i.pravatar.cc/150?img=2",
        ),
        HighScoresItem(
            rank = "3",
            score = 1000,
            name = "Charlie",
            pictureUrl = "https://i.pravatar.cc/150?img=3",
        ),
        HighScoresItem(
            rank = "4",
            score = 1750,
            name = "Dave",
            pictureUrl = "https://i.pravatar.cc/150?img=4",
        ),
    )
    val dummyPersonalBest =
        HighScoresItem(rank = "1", score = 2500, name = "You", pictureUrl = null)
    val state = HomeScreenUiState.Success(
        highScores = dummyHighScores,
        personalBest = dummyPersonalBest,
    )

    PixeliseItTheme {
        HomeContent(state = state)
    }
}

@Preview(showBackground = true, name = "HomeScreen - Error")
@Composable
private fun HomeScreenErrorPreview() {
    val state = HomeScreenUiState.Error(message = Throwable("Something went wrong!"))
    PixeliseItTheme {
        HomeContent(state = state)
    }
}
