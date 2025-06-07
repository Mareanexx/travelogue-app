package ru.mareanexx.feature_profiles.domain.trip

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.data.trip.dto.TripStats
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.ActivityTripWithMapPoints
import ru.mareanexx.feature_profiles.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.feature_profiles.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.feature_profiles.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File

interface TripRepository {
    // get
    suspend fun getActivity() : Flow<BaseResult<List<ActivityTripWithMapPoints>, String>>
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