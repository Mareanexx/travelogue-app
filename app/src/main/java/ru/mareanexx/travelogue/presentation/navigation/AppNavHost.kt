package ru.mareanexx.travelogue.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mareanexx.travelogue.presentation.screens.profile.ProfileScreen
import ru.mareanexx.travelogue.presentation.screens.start.StartScreen


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "start",
    ) {
        composable("start") {
            StartScreen(
                onNavigateToProfile = { navController.navigate(route = "profile") }
            )
        }
        composable("profile") { ProfileScreen() }
    }
}