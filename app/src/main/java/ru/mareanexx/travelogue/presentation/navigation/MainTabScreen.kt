package ru.mareanexx.travelogue.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mareanexx.travelogue.presentation.components.BottomNavBar
import ru.mareanexx.travelogue.presentation.screens.notifications.NotificationsScreen
import ru.mareanexx.travelogue.presentation.screens.profile.ProfileScreen

@Composable
fun MainTabScreen(rootNavController: NavHostController) {
    val navItems = listOf("profile", "notifications", "activity", "explore")
    var selectedTab by rememberSaveable { mutableStateOf("profile") }

    val profileNavController = rememberNavController()
    val notificationsNavController = rememberNavController()
    val activityNavController = rememberNavController()
    val exploreNavController = rememberNavController()

    val navControllers = mapOf(
        "profile" to profileNavController,
        "notifications" to notificationsNavController,
        "activity" to activityNavController,
        "explore" to exploreNavController,
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            navItems.forEach { tab ->
                val navController = navControllers[tab]!!
                val isSelected = tab == selectedTab

                if (isSelected) {
                    NavHost(
                        navController = navController,
                        startDestination = tab,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (tab) {
                            "profile" -> composable("profile") {
                                ProfileScreen(
                                    navigateToActivity = { selectedTab = "activity" },
                                    navigateToNotifications = { selectedTab = "notifications" },
                                    navigateToExplore = { selectedTab = "explore" },
                                    navigateToStartScreen = {
                                        rootNavController.navigate("auth") {
                                            popUpTo("main") { inclusive = true }
                                        }
                                    }
                                )
                            }

                            "notifications" -> composable("notifications") {
                                NotificationsScreen(
                                    navigateToProfile = { selectedTab = "profile" },
                                    navigateToActivity = { selectedTab = "activity" },
                                    navigateToExplore = { selectedTab = "explore" }
                                )
                            }

                            "activity" -> composable("activity") {
                                /* ActivityScreen() */
                            }

                            "explore" -> composable("explore") {
                                /* ExploreScreen() */
                            }
                        }
                    }
                }
            }
        }
    }
}