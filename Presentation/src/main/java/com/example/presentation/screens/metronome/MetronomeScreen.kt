package com.example.presentation.screens.metronome

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.Button


import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.screens.metronome.MetronomeEvent

@Composable
fun MetronomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MetronomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("BPM: ${state.bpm}")
        Slider(
            value = state.bpm.toFloat(),
            onValueChange = { viewModel.onEvent(MetronomeEvent.SetTempo(it.toInt())) },
            valueRange = 40f..240f
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (state.isRunning) {
                    viewModel.onEvent(MetronomeEvent.Stop)
                } else {
                    viewModel.onEvent(MetronomeEvent.Start)
                }
            }
        ) {
            Text(if (state.isRunning) "Stop" else "Start")
        }
    }
}
