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
import com.example.presentation.screens.gallery.GalleryScreen
import com.example.presentation.screens.home.HomeScreen
import com.example.presentation.screens.imagePicker.ImagePickerScreen
import com.example.presentation.screens.imagePicker.navigation.ImagePicker
import com.example.presentation.screens.imageView.ImageViewScreen
import com.example.presentation.screens.imageView.navigation.ImageView
import com.example.presentation.screens.vote.ImageVoteScreen
import com.example.presentation.screens.metronome.MetronomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: AppRoute = AppRoute.Metronome,
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
            HomeScreen()
        }
        composable<AppRoute.ImagePicker> {
            ImagePickerScreen(
                navigateBack = { navController.navigateUp() },
            )
        }
        composable<ImagePicker> {
            ImagePickerScreen(
                navigateBack = { navController.navigateUp() },
            )
        } // little trick to make the ImagePicker screen work with the ImagePicker route with argument as well as in the BottomBar
        composable<AppRoute.ImageVote> {
            ImageVoteScreen()
        }
        composable<AppRoute.Gallery> {
            GalleryScreen(
                navigateToImageView = { imageId ->
                    navController.navigate(ImageView(imageId = imageId))
                },
                navigateToImagePicker = {
                    navController.navigate(ImagePicker(origin = ImagePicker.Companion.Origin.GALLERY))
                },
            )
        }
        composable<ImageView> { _ ->
            ImageViewScreen(
                navigateBack = { navController.navigateUp() },
            )
        }
        composable<AppRoute.Metronome> { _ ->
            MetronomeScreen()
        }
    }
}
