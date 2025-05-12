package ru.mareanexx.travelogue.presentation.screens.othersprofile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.mareanexx.travelogue.presentation.screens.profile.components.skeleton.ProfileContentSkeleton
import ru.mareanexx.travelogue.presentation.screens.profile.components.skeleton.TripsContentSkeleton

@Composable
fun OthersProfileSkeleton() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        ProfileContentSkeleton()
        TripsContentSkeleton()
    }
}