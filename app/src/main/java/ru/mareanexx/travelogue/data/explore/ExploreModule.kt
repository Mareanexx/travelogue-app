package ru.mareanexx.travelogue.data.explore

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.explore.remote.api.ExploreApi
import ru.mareanexx.travelogue.data.explore.repository.ExploreRepositoryImpl
import ru.mareanexx.travelogue.domain.explore.ExploreRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExploreModule {
    @Singleton
    @Provides
    fun provideExploreApi(retrofit: Retrofit): ExploreApi {
        return retrofit.create(ExploreApi::class.java)
    }

    @Singleton
    @Provides
    fun provideExploreRepository(
        userSessionManager: UserSessionManager,
        exploreApi: ExploreApi
    ): ExploreRepository {
        return ExploreRepositoryImpl(userSessionManager, exploreApi)
    }
}