package ru.mareanexx.data.mappoint.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mareanexx.data.mappoint.entity.MapPointEntity

@Dao
interface MapPointDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMapPoints(mapPoints: List<MapPointEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mapPoint: MapPointEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(mapPoint: MapPointEntity)

    @Query("DELETE FROM map_point WHERE id = :mapPointId")
    suspend fun deleteById(mapPointId: Int)

    @Query("SELECT * FROM map_point WHERE trip_id = :tripId")
    suspend fun getMapPointsByTripId(tripId: Int): List<MapPointEntity>
}