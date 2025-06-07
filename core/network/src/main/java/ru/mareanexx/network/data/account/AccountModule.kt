package ru.mareanexx.network.data.account

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.utils.DatabaseCleaner
import ru.mareanexx.network.data.account.remote.api.AccountApi
import ru.mareanexx.network.data.account.repository.AccountRepositoryImpl
import ru.mareanexx.network.domain.account.AccountRepository
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