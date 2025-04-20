package ru.mareanexx.travelogue.data.trip.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mareanexx.travelogue.data.profile.local.entity.ProfileEntity
import ru.mareanexx.travelogue.data.trip.local.converter.LocalDateConverter
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import java.time.LocalDate

@Entity(
    tableName = "trip",
    foreignKeys = [ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["profile_id"],
        onDelete = ForeignKey.CASCADE)
    ]
)
@TypeConverters(value = [LocalDateConverter::class])
data class TripEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val stepsNumber: Int,
    val daysNumber: Int,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String,

    @ColumnInfo(name = "profile_id")
    val profileId: Int
)