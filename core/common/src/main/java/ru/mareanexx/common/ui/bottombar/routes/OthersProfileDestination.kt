package ru.mareanexx.common.ui.bottombar.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument

object OthersProfileDestination {
    private const val ROUTE_NAME = "others-profile"
    const val PROFILE_ID_ARG = "profileId"

    val route = "$ROUTE_NAME/{$PROFILE_ID_ARG}"

    fun buildRoute(profileId: Int) = "$ROUTE_NAME/$profileId"

    val navArguments = listOf(
        navArgument(PROFILE_ID_ARG) { type = NavType.IntType }
    )
}