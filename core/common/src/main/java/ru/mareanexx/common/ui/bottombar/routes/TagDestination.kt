package ru.mareanexx.common.ui.bottombar.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument

object TagDestination {
    private const val ROUTE_NAME = "tag"
    const val TAG_NAME_ARG = "tagName"
    const val IMG_INDEX_ARG = "imgIndex"

    val route = "$ROUTE_NAME/{$TAG_NAME_ARG}/{$IMG_INDEX_ARG}"

    fun buildRoute(tagName: String, imgIndex: Int) = "$ROUTE_NAME/$tagName/$imgIndex"

    val navArguments = listOf(
        navArgument(TAG_NAME_ARG) { type = NavType.StringType },
        navArgument(IMG_INDEX_ARG) { type = NavType.IntType }
    )
}