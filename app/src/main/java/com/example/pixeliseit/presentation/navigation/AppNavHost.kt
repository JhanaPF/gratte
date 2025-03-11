package com.example.pixeliseit.presentation.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pixeliseit.presentation.screens.HomeScreen
import com.example.pixeliseit.presentation.screens.imagePicker.ImagePickerScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: AppRoute = AppRoute.Home,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background),
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { fadeOut() },
        popEnterTransition = { expandHorizontally { it } },
        popExitTransition = { fadeOut()},
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<AppRoute.Home> {
            HomeScreen(
                navigateToImagePicker = { navController.navigate(AppRoute.ImagePicker) }
            )
        }
        composable<AppRoute.ImagePicker> {
            ImagePickerScreen(
                popUp = navController::navigateUp
            )
        }
    }
}