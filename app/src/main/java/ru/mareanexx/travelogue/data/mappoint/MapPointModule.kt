package ru.mareanexx.travelogue.data.mappoint

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.mappoint.local.dao.MapPointDao
import ru.mareanexx.travelogue.data.mappoint.remote.api.MapPointApi
import ru.mareanexx.travelogue.data.mappoint.repository.MapPointRepositoryImpl
import ru.mareanexx.travelogue.data.pointphoto.local.dao.PointPhotoDao
import ru.mareanexx.travelogue.domain.mappoint.MapPointRepository
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