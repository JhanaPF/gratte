package com.example.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
                bottom = 128.dp,
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
                HighScoresList(state = state)
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
fun HighScoresList(state: HomeScreenUiState.Success) {
    LazyColumn {
        item {
            ScoreRow(
                rank = stringResource(R.string.first_row),
                score = stringResource(R.string.second_row),
                name = stringResource(R.string.third_row),
                color = Color.Green,
            )
        }
        itemsIndexed(state.data) { index, item ->
            ScoreRow(
                rank = item.rank,
                score = item.score.toString(),
                name = item.name,
                color = colorForRank(index),
            )
        }
    }
}
