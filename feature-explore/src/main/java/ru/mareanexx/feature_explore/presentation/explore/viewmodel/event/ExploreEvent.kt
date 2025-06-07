package ru.mareanexx.feature_explore.presentation.explore.viewmodel.event

sealed class ExploreEvent {
    data class ShowToast(val message: String) : ExploreEvent()
}