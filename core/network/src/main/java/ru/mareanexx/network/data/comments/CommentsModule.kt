package ru.mareanexx.network.data.comments

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.network.data.comments.remote.api.CommentsApi
import ru.mareanexx.network.data.comments.repository.CommentsRepositoryImpl
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.network.domain.comments.CommentsRepository
import ru.mareanexx.common.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommentsModule {
    @Singleton
    @Provides
    fun provideCommentsApi(retrofit: Retrofit): CommentsApi {
        return retrofit.create(CommentsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCommentsRepository(
        userSession: UserSessionManager,
        profileDao: ru.mareanexx.data.profile.dao.ProfileDao,
        commentsApi: CommentsApi
    ): ru.mareanexx.network.domain.comments.CommentsRepository {
        return CommentsRepositoryImpl(userSession, profileDao, commentsApi)
    }
}