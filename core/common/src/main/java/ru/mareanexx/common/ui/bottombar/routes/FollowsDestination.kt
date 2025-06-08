package ru.mareanexx.common.ui.bottombar.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument

object FollowsDestination {
    private const val ROUTE_NAME = "follows"
    const val PROFILE_ID_ARG = "profileId"
    const val USERNAME_ARG = "username"

    val route = "$ROUTE_NAME/{$USERNAME_ARG}/{$PROFILE_ID_ARG}"

    fun buildRoute(profileId: String, username: String) = "$ROUTE_NAME/$username/$profileId"

    val navArguments = listOf(
        navArgument(PROFILE_ID_ARG) { type = NavType.StringType },
        navArgument(USERNAME_ARG) { type = NavType.StringType }
    )
}