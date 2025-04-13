package com.example.presentation.screens.metronome

sealed class MetronomeEvent {
    data object Start : MetronomeEvent()
    data object Stop : MetronomeEvent()
    data class SetTempo(val bpm: Int) : MetronomeEvent()
}
