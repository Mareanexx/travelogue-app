package ru.mareanexx.travelogue.data.trip

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.mappoint.local.dao.MapPointDao
import ru.mareanexx.travelogue.data.pointphoto.local.dao.PointPhotoDao
import ru.mareanexx.travelogue.data.tag.local.dao.TagDao
import ru.mareanexx.travelogue.data.trip.local.dao.TripDao
import ru.mareanexx.travelogue.data.trip.remote.api.TripApi
import ru.mareanexx.travelogue.data.trip.repository.TripRepositoryImpl
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TripModule {
    @Singleton
    @Provides
    fun provideTripRepository(
        gson: Gson,
        tripApi: TripApi,
        tripDao: TripDao,
        tagDao: TagDao,
        mapPointDao: MapPointDao,
        pointPhotoDao: PointPhotoDao,
        userSessionManager: UserSessionManager
    ): TripRepository {
        return TripRepositoryImpl(gson, tripApi, tripDao, tagDao, mapPointDao, pointPhotoDao, userSessionManager)
    }

    @Singleton
    @Provides
    fun provideTripApi(retrofit: Retrofit): TripApi {
        return retrofit.create(TripApi::class.java)
    }
}