package ru.mareanexx.travelogue.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mareanexx.common.ui.bottombar.BottomNavBar
import ru.mareanexx.common.ui.bottombar.routes.FollowsDestination
import ru.mareanexx.common.ui.bottombar.routes.MainTabs
import ru.mareanexx.common.ui.bottombar.routes.MainTabs.Activity
import ru.mareanexx.common.ui.bottombar.routes.MainTabs.Explore
import ru.mareanexx.common.ui.bottombar.routes.MainTabs.Notifications
import ru.mareanexx.common.ui.bottombar.routes.MainTabs.Profile
import ru.mareanexx.common.ui.bottombar.routes.OthersProfileDestination
import ru.mareanexx.common.ui.bottombar.routes.TagDestination
import ru.mareanexx.common.ui.bottombar.routes.TripDestination
import ru.mareanexx.feature_explore.presentation.screens.ExploreScreen
import ru.mareanexx.feature_notifications.presentation.screen.NotificationsScreen
import ru.mareanexx.feature_profiles.presentation.screens.activity.ActivityScreen
import ru.mareanexx.feature_profiles.presentation.screens.profile.ProfileScreen

@Composable
fun MainTabScreen(rootNavController: NavHostController) {
    val navItems = MainTabs.entries
    var selectedTab by rememberSaveable { mutableStateOf(Profile) }
    val showBottomNavBar = remember { mutableStateOf(true) }
    val scaffoldContainerColor = remember { mutableStateOf(Color.White) }

    val profileNavController = rememberNavController()
    val notificationsNavController = rememberNavController()
    val activityNavController = rememberNavController()
    val exploreNavController = rememberNavController()

    val navControllers = mapOf(
        Profile to profileNavController,
        Notifications to notificationsNavController,
        Activity to activityNavController,
        Explore to exploreNavController,
    )

    Scaffold(
        containerColor = scaffoldContainerColor.value,
        bottomBar = {
            if (showBottomNavBar.value) {
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
                        startDestination = tab.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (tab) {
                            Profile -> {
                                composable(
                                    route = Profile.route,
                                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                                ) {
                                    showBottomNavBar.value = true
                                    scaffoldContainerColor.value = Color.White
                                    ProfileScreen(
                                        navigateToFollows = { username, profileId ->
                                            navController.navigate(FollowsDestination.buildRoute(profileId, username))
                                        },
                                        navigateToStartScreen = {
                                            rootNavController.navigate("auth") {
                                                popUpTo("main") { inclusive = true }
                                            }
                                        },
                                        navigateToTrip = { tripId, username, avatar ->
                                            navController.navigate(TripDestination.buildRoute(tripId, "me", username, avatar))
                                        }
                                    )
                                }

                                commonTabRoutes(
                                    navController = navController,
                                    scaffoldContainerColor = scaffoldContainerColor,
                                    showBottomNavBar = showBottomNavBar
                                )
                            }

                            Notifications -> {
                                composable(Notifications.route) {
                                    showBottomNavBar.value = true
                                    NotificationsScreen(
                                        onNavigateToOthersProfile = { othersProfileId ->
                                            navController.navigate(OthersProfileDestination.buildRoute(othersProfileId))
                                        },
                                        onNavigateToTrip = { tripId, profileId, username, avatar ->
                                            navController.navigate(TripDestination.buildRoute(tripId, profileId, username, avatar))
                                        }
                                    )
                                }

                                commonTabRoutes(
                                    navController = navController,
                                    scaffoldContainerColor = scaffoldContainerColor,
                                    showBottomNavBar = showBottomNavBar
                                )
                            }

                            Activity -> {
                                composable(
                                    route = Activity.route,
                                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                                ) {
                                    showBottomNavBar.value = true
                                    scaffoldContainerColor.value = Color.White
                                    ActivityScreen(
                                        onNavigateToSearch = { selectedTab = Explore },
                                        onNavigateToOthersProfile = { othersProfileId ->
                                            navController.navigate(OthersProfileDestination.buildRoute(othersProfileId))
                                        },
                                        onNavigateToTrip = { tripId, profileId, username, avatar ->
                                            navController.navigate(TripDestination.buildRoute(tripId, profileId, username, avatar))
                                        }
                                    )
                                }

                                commonTabRoutes(
                                    navController = navController,
                                    scaffoldContainerColor = scaffoldContainerColor,
                                    showBottomNavBar = showBottomNavBar
                                )
                            }

                            Explore -> {
                                composable(
                                    route = Explore.route,
                                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                                ) {
                                    showBottomNavBar.value = true
                                    scaffoldContainerColor.value = Color.White
                                    ExploreScreen(
                                        onNavigateToTrip = { tripId, profileId, username, avatar ->
                                            navController.navigate(TripDestination.buildRoute(tripId, profileId, username, avatar))
                                        },
                                        onNavigateToOthersProfile = { othersProfileId ->
                                            navController.navigate(OthersProfileDestination.buildRoute(othersProfileId))
                                        },
                                        onNavigateToTagScreen = { imgIndex, tagName ->
                                            navController.navigate(TagDestination.buildRoute(tagName, imgIndex))
                                        }
                                    )
                                }

                                commonTabRoutes(
                                    navController = navController,
                                    scaffoldContainerColor = scaffoldContainerColor,
                                    showBottomNavBar = showBottomNavBar
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}