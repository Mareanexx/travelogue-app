package ru.mareanexx.feature_profiles.data.mappoint.remote.dto

import ru.mareanexx.feature_profiles.domain.mappoint.entity.MapPoint
import ru.mareanexx.feature_profiles.domain.mappoint.entity.PointPhoto

data class MapPointWithPhotos(
    val mapPoint: MapPoint,
    val photos: List<PointPhoto>
)