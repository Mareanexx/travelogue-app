package ru.mareanexx.feature_explore.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_explore.data.remote.api.ExploreApi
import ru.mareanexx.feature_explore.data.repository.ExploreRepositoryImpl
import ru.mareanexx.feature_explore.domain.ExploreRepository
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