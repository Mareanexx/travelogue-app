package ru.mareanexx.travelogue.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.mareanexx.common.ui.bottombar.routes.FollowsDestination
import ru.mareanexx.common.ui.bottombar.routes.OthersProfileDestination
import ru.mareanexx.common.ui.bottombar.routes.TagDestination
import ru.mareanexx.common.ui.bottombar.routes.TripDestination
import ru.mareanexx.common.ui.theme.mapBoxBackground
import ru.mareanexx.feature_explore.presentation.screens.TagScreen
import ru.mareanexx.feature_profiles.presentation.screens.follows.FollowsScreen
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.OthersProfileScreen
import ru.mareanexx.feature_profiles.presentation.screens.trip.TripScreen

fun NavGraphBuilder.commonTabRoutes(
    navController: NavHostController,
    scaffoldContainerColor: MutableState<Color>,
    showBottomNavBar: MutableState<Boolean>,
    defaultProfileId: String = "me"
) {
    composable(
        route = OthersProfileDestination.route,
        arguments = OthersProfileDestination.navArguments,
        enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
    ) { backStackEntry ->
        showBottomNavBar.value = true
        scaffoldContainerColor.value = Color.White
        val argProfileId = backStackEntry.arguments?.getInt(OthersProfileDestination.PROFILE_ID_ARG) ?: -1
        OthersProfileScreen(
            profileId = argProfileId,
            navigateToFollows = { username, profileId ->
                navController.navigate(FollowsDestination.buildRoute(profileId, username))
            },
            navigateToTrip = { tripId, username, avatar ->
                navController.navigate(TripDestination.buildRoute(tripId, argProfileId.toString(), username, avatar))
            },
            navigateBack = { navController.popBackStack() }
        )
    }

    composable(
        route = FollowsDestination.route,
        arguments = FollowsDestination.navArguments
    ) { backStackEntry ->
        showBottomNavBar.value = true
        val profileId = backStackEntry.arguments?.getString(FollowsDestination.PROFILE_ID_ARG) ?: ""
        val username = backStackEntry.arguments?.getString(FollowsDestination.USERNAME_ARG) ?: "Username"
        FollowsScreen(
            username = username,
            profileId = profileId,
            onNavigateToOthersProfile = { othersProfileId ->
                navController.navigate(OthersProfileDestination.buildRoute(othersProfileId))
            },
            navigateBack = { navController.popBackStack() }
        )
    }

    composable(
        route = TripDestination.route,
        arguments = TripDestination.navArguments
    ) { backStackEntry ->
        showBottomNavBar.value = false
        scaffoldContainerColor.value = mapBoxBackground
        val profileId = backStackEntry.arguments?.getString(TripDestination.PROFILE_ID_ARG) ?: defaultProfileId
        val tripId = backStackEntry.arguments?.getInt(TripDestination.TRIP_ID_ARG) ?: -1
        val avatar = backStackEntry.arguments?.getString(TripDestination.AVATAR) ?: ""
        val username = backStackEntry.arguments?.getString(TripDestination.USERNAME_ARG) ?: ""
        TripScreen(
            profileId = profileId,
            tripId = tripId,
            username = username,
            userAvatar = avatar,
            onNavigateToOthersProfile = { othersProfileId ->
                navController.navigate(OthersProfileDestination.buildRoute(othersProfileId))
            },
            onNavigateToConcreteTagScreen = { tagName, imgIndex ->
                navController.navigate(TagDestination.buildRoute(tagName, imgIndex))
            },
            navigateBack = { navController.popBackStack() }
        )
    }

    composable(
        route = TagDestination.route,
        arguments = TagDestination.navArguments,
        enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
    ) { backStackEntry ->
        showBottomNavBar.value = false
        scaffoldContainerColor.value = Color.White
        val tagName = backStackEntry.arguments?.getString(TagDestination.TAG_NAME_ARG) ?: ""
        val imgIndex = backStackEntry.arguments?.getInt(TagDestination.IMG_INDEX_ARG) ?: 0
        TagScreen(
            tagName = tagName,
            imgIndex = imgIndex,
            navigateBack = { navController.popBackStack() },
            onNavigateToOthersProfile = { othersProfileId ->
                navController.navigate(OthersProfileDestination.buildRoute(othersProfileId))
            },
            onNavigateToTrip = { tripId, profileId, username, avatar ->
                navController.navigate(TripDestination.buildRoute(tripId, profileId, username, avatar))
            }
        )
    }
}