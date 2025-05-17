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
import com.example.presentation.screens.metronome.MetronomeViewModel


import kotlinx.coroutines.flow.collect
import androidx.compose.ui.platform.LocalContext
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log

@Composable
fun MetronomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MetronomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    // ⚡ Collect tick events
    LaunchedEffect(Unit) {
        viewModel.tickEvent.collect {
            println("Tick!")

            //MediaPlayer.create(context, R.raw.metronome_sound).start()


            val resId = context.resources.getIdentifier("metronome", "raw", context.packageName)


            if (resId != 0) {
                try {
                    val mediaPlayer = MediaPlayer.create(context, resId)
                    mediaPlayer?.setOnCompletionListener {
                        it.release()
                    }
                    mediaPlayer?.start()
                } catch (e: Exception) {
                    Log.e("AUDIO", "Erreur lecture MediaPlayer: ${e.message}")
                }
            } else {
                Log.e("AUDIO", "Fichier introuvable")
            }
        }
    }

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
