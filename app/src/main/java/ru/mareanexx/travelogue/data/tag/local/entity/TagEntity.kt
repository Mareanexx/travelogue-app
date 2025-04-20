package ru.mareanexx.travelogue.data.tag.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity

@Entity(
    tableName = "tags",
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["trip_id"],
        onDelete = ForeignKey.CASCADE)
    ]
)
data class TagEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "trip_id")
    val tripId: Int,
    val name: String
)