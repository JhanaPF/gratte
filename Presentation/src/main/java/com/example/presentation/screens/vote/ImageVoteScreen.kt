package com.example.presentation.screens.vote

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.composables.ErrorContent
import com.example.presentation.composables.ImageItem
import com.example.presentation.composables.LoadingIndicator
import com.example.presentation.theme.PixeliseItTheme
import com.example.presentation.theme.retro
import com.example.presentation.utils.randomFlashyColor
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ImageVoteScreen(
    viewModel: ImageVoteScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PixeliseItTheme {
        ImageVoteScreenContent(
            state = state,
        )
    }
}

@Composable
fun ImageVoteScreenContent(
    state: ImageVoteScreenUiState,
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
                .padding(bottom = 24.dp),
            text = stringResource(id = R.string.vote_title),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = retro,
            fontSize = 24.sp,
        )

        when (state) {
            is ImageVoteScreenUiState.Loading -> {
                LoadingIndicator()
            }

            is ImageVoteScreenUiState.EndOfProfiles -> {
                EndOfProfiles()
            }

            is ImageVoteScreenUiState.Success -> {
                ProfileList(state = state)
            }

            is ImageVoteScreenUiState.Error -> {
                ErrorContent(throwable = state.message)
            }
        }
    }
}

@Composable
fun ProfileList(
    state: ImageVoteScreenUiState.Success,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            state.pagerItems.size
        },
        initialPage = 0,
    )

    val fastFlingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = tween(durationMillis = 300),
    )

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp)
                .border(2.dp, randomFlashyColor()),
        ) {
            HorizontalPager(
                modifier = Modifier
                    .align(Alignment.Center),
                userScrollEnabled = false,
                state = pagerState,
                beyondViewportPageCount = 1,
                flingBehavior = fastFlingBehavior,
            ) { page ->
                val pageModifier = pagerModifier(pagerState.getOffsetDistanceInPages(page))

                ImageItem(
                    imageUrl = state.pagerItems[page].pictureUrl,
                    contentScale = ContentScale.FillHeight,
                    score = state.pagerItems[pagerState.currentPage].score,
                    modifier = pageModifier,
                )
            }
            Text(
                text = state.pagerItems[pagerState.currentPage].name,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = retro,
                fontSize = 24.sp,
            )
        }
        VoteButtons(
            onVotePositive = {
                state.onVotePositive()
                onNextPage(
                    scope = coroutineScope,
                    pagerState = pagerState,
                    size = state.pagerItems.size,
                    onLastPage = {
                        state.onLastProfile()
                    },
                )
            },
            onVoteNegative =
            {
                state.onVoteNegative()
                onNextPage(
                    scope = coroutineScope,
                    pagerState = pagerState,
                    size = state.pagerItems.size,
                    onLastPage = {
                        state.onLastProfile()
                    },
                )
            },
        )
    }
}

@Composable
private fun VoteButtons(
    onVotePositive: () -> Unit,
    onVoteNegative: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
    ) {
        IconButton(
            modifier = Modifier
                .size(96.dp),
            onClick = {
                onVoteNegative()
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.icn_reject),
                contentDescription = "Reject",
            )
        }
        IconButton(
            modifier = Modifier
                .size(96.dp),
            onClick = {
                onVotePositive()
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.icn_validate),
                contentDescription = "Validate",
            )
        }
    }
}

fun onNextPage(
    scope: CoroutineScope,
    pagerState: PagerState,
    size: Int,
    onLastPage: () -> Unit,
) {
    scope.launch {
        if (pagerState.currentPage < size - 1) {
            pagerState.animateScrollToPage(
                page = pagerState.currentPage + 1,
                animationSpec = tween(durationMillis = 300),
            )
        } else {
            onLastPage()
        }
    }
}

@Composable
fun EndOfProfiles(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn() + scaleIn(initialScale = 0.7f),
        exit = fadeOut() + scaleOut(targetScale = 0.7f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.vote_end_of_profiles),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = retro,
                fontSize = 24.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessPreview() {
    val dummyPagerItems = persistentListOf(
        PagerItem("Alice", "https://i.pravatar.cc/700", 1500),
        PagerItem("Bob", "https://i.pravatar.cc/650", 2000),
    )

    val successState = ImageVoteScreenUiState.Success(
        pagerItems = dummyPagerItems,
        onVotePositive = {},
        onVoteNegative = {},
        onLastProfile = {},
    )

    PixeliseItTheme {
        ImageVoteScreenContent(state = successState)
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    val errorState = ImageVoteScreenUiState.Error(message = Throwable("An error occurred"))

    PixeliseItTheme {
        ImageVoteScreenContent(state = errorState)
    }
}

@Preview(showBackground = true)
@Composable
private fun EndOfProfilesPreview() {
    PixeliseItTheme {
        EndOfProfiles()
    }
}
