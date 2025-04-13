package com.example.presentation.screens.metronome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.screens.metronome.MetronomeEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MetronomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MetronomeUIState())
    val uiState: StateFlow<MetronomeUIState> = _uiState

    private var metronomeJob: Job? = null

    fun onEvent(event: MetronomeEvent) {
        when (event) {
            is MetronomeEvent.Start -> startMetronome()
            is MetronomeEvent.Stop -> stopMetronome()
            is MetronomeEvent.SetTempo -> updateTempo(event.bpm)
        }
    }

    private fun startMetronome() {
        if (_uiState.value.isRunning) return
        _uiState.value = _uiState.value.copy(isRunning = true)
        metronomeJob = viewModelScope.launch {
            while (_uiState.value.isRunning) {
                // 🔊 Simule un "tick"
                println("Tick!")
                delay((60000 / _uiState.value.bpm).toLong())
            }
        }
    }

    private fun stopMetronome() {
        _uiState.value = _uiState.value.copy(isRunning = false)
        metronomeJob?.cancel()
        metronomeJob = null
    }

    private fun updateTempo(newBpm: Int) {
        _uiState.value = _uiState.value.copy(bpm = newBpm)
        if (_uiState.value.isRunning) {
            stopMetronome()
            startMetronome()
        }
    }
}
