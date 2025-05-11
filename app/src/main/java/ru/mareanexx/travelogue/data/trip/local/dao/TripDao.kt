package ru.mareanexx.travelogue.data.trip.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity
import ru.mareanexx.travelogue.domain.trip.entity.TripStatsResponse

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

    @Query("SELECT * FROM trip WHERE id = :tripId")
    suspend fun getTripById(tripId: Int): TripEntity?

    @Query("""
        SELECT 
            tr.id AS id,
            tr.startDate AS startDate,
            COUNT(mp.id) AS stepsNumber,
            MAX(mp.arrivalDate) AS maxArrivalDate
        FROM trip tr
        LEFT JOIN map_point mp ON mp.trip_id = tr.id
        GROUP BY tr.id, tr.startDate
    """)
    suspend fun getTripStats(): List<TripStatsResponse>
}