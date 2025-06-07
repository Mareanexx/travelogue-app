package ru.mareanexx.feature_profiles.data.mappoint

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.data.mappoint.dao.MapPointDao
import ru.mareanexx.data.pointphoto.dao.PointPhotoDao
import ru.mareanexx.feature_profiles.data.mappoint.remote.api.MapPointApi
import ru.mareanexx.feature_profiles.data.mappoint.repository.MapPointRepositoryImpl
import ru.mareanexx.feature_profiles.domain.mappoint.MapPointRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapPointModule {
    @Singleton
    @Provides
    fun provideMapPointApi(retrofit: Retrofit): MapPointApi {
        return retrofit.create(MapPointApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMapPointRepository(
        gson: Gson,
        mapPointDao: MapPointDao,
        pointPhotoDao: PointPhotoDao,
        mapPointApi: MapPointApi
    ): MapPointRepository {
        return MapPointRepositoryImpl(gson, mapPointDao, pointPhotoDao, mapPointApi)
    }
}