package ru.mareanexx.network.data.follows

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.network.data.follows.remote.api.FollowsApi
import ru.mareanexx.network.data.follows.repository.FollowsRepositoryImpl
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
    ): ru.mareanexx.network.domain.follows.FollowsRepository {
        return FollowsRepositoryImpl(followsApi, userSessionManager)
    }
}