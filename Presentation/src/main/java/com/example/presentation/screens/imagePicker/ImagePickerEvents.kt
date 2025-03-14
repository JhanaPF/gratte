package com.example.presentation.screens.imagePicker

sealed interface ImagePickerEvents {
    data object ShowErrorSnackBar : ImagePickerEvents
}
