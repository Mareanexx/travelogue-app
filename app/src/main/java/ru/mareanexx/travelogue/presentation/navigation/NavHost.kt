package ru.mareanexx.travelogue.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mareanexx.feature_auth.presentation.screens.StartScreen


@Composable
fun AppNavHost(rootNavController: NavHostController, startDestination: String) {
    NavHost(
        navController = rootNavController,
        startDestination = startDestination
    ) {
        composable("auth") {
            StartScreen(
                onNavigateToMain = {
                    rootNavController.navigate("main") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            MainTabScreen(rootNavController)
        }
    }
}