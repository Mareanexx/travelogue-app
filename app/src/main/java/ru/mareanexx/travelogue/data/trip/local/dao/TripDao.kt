package ru.mareanexx.travelogue.data.trip.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrips(newTrips: List<TripEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(trip: TripEntity)

    @Query("DELETE FROM trip WHERE id = :tripId")
    suspend fun deleteTripById(tripId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newTrip: TripEntity)

    @Query("SELECT * FROM trip ORDER BY startDate DESC")
    suspend fun getTrips(): List<TripEntity>
}