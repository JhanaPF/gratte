package com.example.presentation.screens.metronome

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button


import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.screens.metronome.MetronomeEvent
import com.example.presentation.screens.metronome.MetronomeViewModel


import kotlinx.coroutines.flow.collect
import androidx.compose.ui.platform.LocalContext
import android.media.MediaPlayer


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

import android.media.SoundPool
import android.media.AudioAttributes


@Composable
fun MetronomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MetronomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    val soundPool = remember {
        SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    var soundId by remember { mutableStateOf(0) }
    var soundReady by remember { mutableStateOf(false) }

    val resId = context.resources.getIdentifier("metronome", "raw", context.packageName)
    if (resId != 0) {
        soundId = soundPool.load(context, resId, 1)

        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0 && sampleId == soundId) {
                soundReady = true
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.tickEvent.collect {

            if (soundReady && state.isRunning) {
                soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
                //println("Tick!")
            }

        }
    }

    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
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
                    fontFamily = flamenco,
                    fontSize = 28.sp,
                )
            }

            Text(
                text = "${state.bpm}",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = flamenco,
                fontSize = 30.sp,
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
                    fontFamily = flamenco,
                    fontSize = 28.sp,
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
                    fontFamily = flamenco,
                    fontSize = 32.sp,
                )
            }
        }

    }
}
