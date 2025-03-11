package com.example.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.composables.ScoreRow
import com.example.presentation.theme.PixeliseItTheme
import com.example.presentation.theme.retro

@Composable
fun HomeScreen(
    navigateToImagePicker: () -> Unit
) {
    PixeliseItTheme {
        Scaffold { innerPadding ->
            HomeContent(
                navigateToImagePicker = navigateToImagePicker,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HomeContent(
    navigateToImagePicker: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 128.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = retro,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.padding(24.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp),
                text = stringResource(id = R.string.home_title),
                fontFamily = retro,
                fontSize = 24.sp
            )

            LazyColumn {
//                items(30) {
//                    ScoreRow(
//                        rank = 1,
//                        score = 1000,
//                        name = "John Doe",
//                        color = Color.Red
//                    )
//                }
            }
        }
        Button(
            onClick = {
                navigateToImagePicker()
            },
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text("Open Image Picker")
        }
    }
}