package ru.mareanexx.travelogue.data.comments

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.comments.remote.api.CommentsApi
import ru.mareanexx.travelogue.data.comments.repository.CommentsRepositoryImpl
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.domain.comments.CommentsRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
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
        profileDao: ProfileDao,
        commentsApi: CommentsApi
    ): CommentsRepository {
        return CommentsRepositoryImpl(userSession, profileDao, commentsApi)
    }
}