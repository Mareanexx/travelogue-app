package ru.mareanexx.feature_profiles.data.trip.remote.dto

import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip

data class TripWithMapPoints(
    val trip: Trip,
    val mapPoints: List<MapPointWithPhotos>
)