package ru.mareanexx.travelogue.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mareanexx.travelogue.presentation.screens.start.StartScreen


@Composable
fun AppNavHost(rootNavController: NavHostController) {
    NavHost(
        navController = rootNavController,
        startDestination = "main"
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