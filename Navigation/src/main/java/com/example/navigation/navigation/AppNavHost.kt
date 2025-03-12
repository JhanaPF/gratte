package com.example.navigation.navigation

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
import androidx.navigation.toRoute
import com.example.presentation.screens.HomeScreen
import com.example.presentation.screens.gallery.GalleryScreen
import com.example.presentation.screens.imagePicker.ImagePickerScreen
import com.example.presentation.screens.imageView.ImageViewScreen

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
        popExitTransition = { fadeOut() },
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<AppRoute.Home> {
            HomeScreen(
                navigateToImagePicker = { navController.navigate(AppRoute.ImagePicker) },
            )
        }
        composable<AppRoute.ImagePicker> {
            ImagePickerScreen()
        }
        composable<AppRoute.Gallery> {
            GalleryScreen(
                navigateToImageView = { imageId ->
                    navController.navigate(AppRoute.ImageView(imageId = imageId))
                },
                navigateToImagePicker = { navController.navigate(AppRoute.ImagePicker) },
            )
        }
        composable<AppRoute.ImageView> { backStackEntry ->
            val image = backStackEntry.toRoute<AppRoute.ImageView>()
            ImageViewScreen(
                imageId = image.imageId,
                navigateBack = { navController.navigateUp() },
            )
        }
    }
}
