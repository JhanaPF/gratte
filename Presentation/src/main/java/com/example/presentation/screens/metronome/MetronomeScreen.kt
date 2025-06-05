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

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.theme.flamenco


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

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = {
                    val newBpm = (state.bpm - 5).coerceAtLeast(40)
                    viewModel.onEvent(MetronomeEvent.SetTempo(newBpm))
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            ) {
                Text(
                    text = "-5",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    //color = MaterialTheme.colorScheme.primary,
                    fontFamily = flamenco,
                    //fontSize = 32.sp,
                    //fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "${state.bpm}",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = flamenco,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {
                    val newBpm = (state.bpm + 5).coerceAtMost(240) // limite max 240
                    viewModel.onEvent(MetronomeEvent.SetTempo(newBpm))
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            ) {
                Text(
                    text = "+5",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    //color = MaterialTheme.colorScheme.primary,
                    fontFamily = flamenco,
                    //fontSize = 32.sp,
                    //fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    if (state.isRunning) {
                        viewModel.onEvent(MetronomeEvent.Stop)
                    } else {
                        viewModel.onEvent(MetronomeEvent.Start)
                    }
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            ) {
                Text(
                    text = if (state.isRunning) "Stop" else "Start",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    //color = MaterialTheme.colorScheme.primary,
                    fontFamily = flamenco,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}
