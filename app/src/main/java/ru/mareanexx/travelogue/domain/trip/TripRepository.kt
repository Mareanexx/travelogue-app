package ru.mareanexx.travelogue.domain.trip

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.travelogue.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import ru.mareanexx.travelogue.domain.trip.entity.TripStats
import java.io.File

interface TripRepository {
    // get
    suspend fun getTaggedTrips(tagName: String) : Flow<BaseResult<List<TrendingTrip>, String>>
    suspend fun getUpdatedTripStats() : Flow<List<TripStats>>
    suspend fun getTripFromDatabase(tripId: Int) : Flow<BaseResult<TripWithMapPoints, String>>
    suspend fun fetchTripFromNetwork(tripId: Int) : Flow<BaseResult<TripWithMapPoints, String>>
    suspend fun getTripWithMapPoints(profileId: String, tripId: Int) : Flow<BaseResult<TripWithMapPoints, String>>
    suspend fun getAuthorsTrips() : Flow<List<Trip>>
    // add
    suspend fun addNewTrip(newTripRequest: NewTripRequest, coverPhoto: File) : Flow<BaseResult<Trip, String>>
    // update
    suspend fun updateTrip(editTripRequest: EditTripRequest, coverPhoto: File?) : Flow<BaseResult<Trip, String>>
    // delete
    suspend fun deleteTrip(tripId: Int) : Flow<BaseResult<String, String>>
}