package ru.mareanexx.feature_profiles.data.pointphoto.mapper

import ru.mareanexx.data.pointphoto.entity.PointPhotoEntity
import ru.mareanexx.feature_profiles.data.pointphoto.remote.dto.DeletedPhoto
import ru.mareanexx.feature_profiles.domain.mappoint.entity.PointPhoto

fun PointPhotoEntity.toResponse() = PointPhoto(
    id = id,
    filePath = filePath,
    mapPointId = mapPointId
)

fun PointPhoto.toEntity() = PointPhotoEntity(
    id = id,
    filePath = filePath,
    mapPointId = mapPointId
)

fun PointPhoto.toDeleted() = DeletedPhoto(
    id = id,
    filePath = filePath
)