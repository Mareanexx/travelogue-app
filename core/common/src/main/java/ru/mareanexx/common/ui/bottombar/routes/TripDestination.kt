package ru.mareanexx.common.ui.bottombar.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument

object TripDestination {
    private const val ROUTE_NAME = "trip"

    const val TRIP_ID_ARG = "tripId"
    const val PROFILE_ID_ARG = "profileId"
    const val USERNAME_ARG = "username"
    const val AVATAR = "avatar"

    val route = "$ROUTE_NAME/?$TRIP_ID_ARG={$TRIP_ID_ARG}&$PROFILE_ID_ARG={$PROFILE_ID_ARG}&$USERNAME_ARG={$USERNAME_ARG}&$AVATAR={$AVATAR}"

    fun buildRoute(tripId: Int, profileId: String, username: String, avatar: String) =
        "$ROUTE_NAME/?tripId=$tripId&profileId=$profileId&username=$username&avatar=$avatar"

    val navArguments = listOf(
        navArgument(TRIP_ID_ARG) { type = NavType.IntType },
        navArgument(PROFILE_ID_ARG) { type = NavType.StringType },
        navArgument(USERNAME_ARG) { type = NavType.StringType },
        navArgument(AVATAR) { type = NavType.StringType }
    )
}