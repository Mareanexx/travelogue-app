package ru.mareanexx.data.tag.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags",
    foreignKeys = [ForeignKey(
        entity = ru.mareanexx.data.trip.entity.TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["trip_id"],
        onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["trip_id"])]
)
data class TagEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "trip_id")
    val tripId: Int,
    val name: String
)