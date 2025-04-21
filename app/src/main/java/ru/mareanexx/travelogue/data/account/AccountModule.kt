package ru.mareanexx.travelogue.data.account

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.account.remote.api.AccountApi
import ru.mareanexx.travelogue.data.account.repository.AccountRepositoryImpl
import ru.mareanexx.travelogue.domain.account.AccountRepository
import ru.mareanexx.travelogue.utils.DatabaseCleaner
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
    @Singleton
    @Provides
    fun provideAccountRepository(
        userSessionManager: UserSessionManager,
        accountApi: AccountApi,
        databaseCleaner: DatabaseCleaner
    ): AccountRepository {
        return AccountRepositoryImpl(userSessionManager, accountApi, databaseCleaner)
    }

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }
}