package com.example.presentation.screens.imagePicker

sealed interface ImagePickerEvents {
    data object NavigateBack : ImagePickerEvents
    data class ShowErrorSnackBar(val message: String) : ImagePickerEvents
}
