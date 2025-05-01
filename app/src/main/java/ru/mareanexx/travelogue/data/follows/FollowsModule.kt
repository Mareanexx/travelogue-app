package ru.mareanexx.travelogue.data.follows

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.follows.remote.api.FollowsApi
import ru.mareanexx.travelogue.data.follows.repository.FollowsRepositoryImpl
import ru.mareanexx.travelogue.domain.follows.FollowsRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FollowsModule {
    @Provides
    @Singleton
    fun provideFollowsApi(retrofit: Retrofit): FollowsApi {
        return retrofit.create(FollowsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFollowsRepository(
        followsApi: FollowsApi,
        userSessionManager: UserSessionManager
    ): FollowsRepository {
        return FollowsRepositoryImpl(followsApi, userSessionManager)
    }
}