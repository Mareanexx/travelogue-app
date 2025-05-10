package ru.mareanexx.travelogue.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.mareanexx.travelogue.presentation.components.BottomNavBar
import ru.mareanexx.travelogue.presentation.screens.explore.ExploreScreen
import ru.mareanexx.travelogue.presentation.screens.follows.FollowsScreen
import ru.mareanexx.travelogue.presentation.screens.notifications.NotificationsScreen
import ru.mareanexx.travelogue.presentation.screens.profile.ProfileScreen
import ru.mareanexx.travelogue.presentation.screens.trip.TripScreen
import ru.mareanexx.travelogue.presentation.theme.mapBoxBackground

@Composable
fun MainTabScreen(rootNavController: NavHostController) {
    val navItems = listOf("profile", "notifications", "activity", "explore")
    var selectedTab by rememberSaveable { mutableStateOf("profile") }
    var showBottomNavBar by remember { mutableStateOf(true) }
    val scaffoldContainerColor = remember { mutableStateOf(Color.White) }

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
        containerColor = scaffoldContainerColor.value,
        bottomBar = {
            if (showBottomNavBar) {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
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
                            "profile" -> {
                                composable("profile") {
                                    showBottomNavBar = true
                                    scaffoldContainerColor.value = Color.White
                                    ProfileScreen(
                                        navigateToFollows = { username, profileId ->
                                            navController.navigate("follows/$username/$profileId")
                                        },
                                        navigateToStartScreen = {
                                            rootNavController.navigate("auth") {
                                                popUpTo("main") { inclusive = true }
                                            }
                                        },
                                        navigateToTrip = { tripId, username, avatar ->
                                            navController.navigate("trip/?tripId=$tripId" +
                                                    "&profileId=me" +
                                                    "&username=$username" +
                                                    "&avatar=$avatar")
                                        }
                                    )
                                }

                                composable(
                                    route = "follows/{username}/{profileId}",
                                    arguments = listOf(
                                        navArgument("profileId") {
                                            type = NavType.IntType
                                        },
                                        navArgument("username") {
                                            type = NavType.StringType
                                        }
                                    )
                                ) { backStackEntry ->
                                    showBottomNavBar = true
                                    val profileId = backStackEntry.arguments?.getInt("profileId") ?: -1
                                    val username = backStackEntry.arguments?.getString("username") ?: "Username"
                                    FollowsScreen(
                                        username = username,
                                        profileId = profileId,
                                        navigateBack = { navController.popBackStack() }
                                    )
                                }

                                composable(route = "trip/?tripId={tripId}&profileId={profileId}&username={username}&avatar={avatar}",
                                    arguments = listOf(
                                        navArgument("tripId") { type = NavType.IntType },
                                        navArgument("profileId") { type = NavType.StringType },
                                        navArgument("username") { type = NavType.StringType },
                                        navArgument("avatar") { type = NavType.StringType }
                                    )
                                ) { backStackEntry ->
                                    showBottomNavBar = false
                                    scaffoldContainerColor.value = mapBoxBackground
                                    val profileId = backStackEntry.arguments?.getString("profileId") ?: "me"
                                    val tripId = backStackEntry.arguments?.getInt("tripId") ?: -1
                                    val avatar = backStackEntry.arguments?.getString("avatar") ?: ""
                                    val username = backStackEntry.arguments?.getString("username") ?: ""
                                    TripScreen(
                                        profileId = profileId,
                                        tripId = tripId,
                                        username = username,
                                        userAvatar = avatar,
                                        navigateBack = { navController.popBackStack() }
                                    )
                                }
                            }

                            "notifications" -> composable("notifications") {
                                showBottomNavBar = true
                                NotificationsScreen()
                            }

                            "activity" -> composable("activity") {
                                showBottomNavBar = true
                                /* ActivityScreen() */
                            }

                            "explore" -> composable("explore") {
                                showBottomNavBar = true
                                ExploreScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}