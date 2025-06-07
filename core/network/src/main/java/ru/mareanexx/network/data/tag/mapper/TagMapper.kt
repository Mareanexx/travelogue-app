package ru.mareanexx.network.data.tag.mapper

import ru.mareanexx.data.tag.entity.TagEntity
import ru.mareanexx.network.data.tag.remote.dto.NewTagResponse
import ru.mareanexx.network.domain.tag.entity.Tag

fun Tag.toEntity() = TagEntity(
    id = id,
    name = name,
    tripId = tripId
)

fun NewTagResponse.toEntity(tripId: Int) = TagEntity(
    id = id,
    name = name,
    tripId = tripId
)

fun TagEntity.toResponse() = NewTagResponse(
    id = id,
    name = name
)