package ru.mareanexx.feature_profiles.data.trip

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.mappoint.dao.MapPointDao
import ru.mareanexx.data.pointphoto.dao.PointPhotoDao
import ru.mareanexx.data.tag.dao.TagDao
import ru.mareanexx.data.trip.dao.TripDao
import ru.mareanexx.feature_profiles.data.trip.remote.api.TripApi
import ru.mareanexx.feature_profiles.data.trip.repository.TripRepositoryImpl
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
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