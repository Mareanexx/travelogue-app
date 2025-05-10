package ru.mareanexx.travelogue.domain.trip

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.travelogue.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import java.io.File

interface TripRepository {
    suspend fun getTripFromDatabase(tripId: Int) : Flow<BaseResult<TripWithMapPoints, String>>
    suspend fun fetchTripFromNetwork(tripId: Int) : Flow<BaseResult<TripWithMapPoints, String>>
    suspend fun getTripWithMapPoints(profileId: String, tripId: Int) : Flow<BaseResult<TripWithMapPoints, String>>
    suspend fun getAuthorsTrips() : Flow<List<Trip>>
    suspend fun addNewTrip(newTripRequest: NewTripRequest, coverPhoto: File) : Flow<BaseResult<Trip, String>>
    suspend fun updateTrip(editTripRequest: EditTripRequest, coverPhoto: File?) : Flow<BaseResult<Trip, String>>
    suspend fun deleteTrip(tripId: Int) : Flow<BaseResult<String, String>>
}