package ru.mareanexx.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mareanexx.data.mappoint.dao.MapPointDao
import ru.mareanexx.data.mappoint.entity.MapPointEntity
import ru.mareanexx.data.pointphoto.dao.PointPhotoDao
import ru.mareanexx.data.pointphoto.entity.PointPhotoEntity
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.data.profile.entity.ProfileEntity
import ru.mareanexx.data.tag.dao.TagDao
import ru.mareanexx.data.tag.entity.TagEntity
import ru.mareanexx.data.trip.dao.TripDao
import ru.mareanexx.data.trip.entity.TripEntity

@Database(
    entities = [
        ProfileEntity::class,
        TripEntity::class,
        TagEntity::class,
        MapPointEntity::class,
        PointPhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TravelogueDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun tripDao(): TripDao
    abstract fun tagDao(): TagDao
    abstract fun mapPointDao(): MapPointDao
    abstract fun pointPhotoDao(): PointPhotoDao
}