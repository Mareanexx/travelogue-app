package ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.event

sealed class ExploreEvent {
    data class ShowToast(val message: String) : ExploreEvent()
}