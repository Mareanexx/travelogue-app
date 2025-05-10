package ru.mareanexx.travelogue.data.pointphoto.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mareanexx.travelogue.data.pointphoto.local.entity.PointPhotoEntity

@Dao
interface PointPhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pointPhotos: List<PointPhotoEntity>)

    @Query("DELETE FROM point_photo WHERE map_point_id = :mapPointId")
    suspend fun deleteByMapPointId(mapPointId: Int)

    @Query("SELECT * FROM point_photo ph JOIN map_point mp ON mp.id = ph.map_point_id WHERE mp.trip_id = :tripId")
    suspend fun getAll(tripId: Int): List<PointPhotoEntity>
}