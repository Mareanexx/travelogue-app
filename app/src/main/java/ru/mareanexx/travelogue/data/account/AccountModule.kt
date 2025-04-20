package ru.mareanexx.travelogue.data.account

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.account.remote.api.AccountApi
import ru.mareanexx.travelogue.data.account.repository.AccountRepositoryImpl
import ru.mareanexx.travelogue.domain.account.AccountRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
    @Singleton
    @Provides
    fun provideAccountRepository(
        userSessionManager: UserSessionManager,
        accountApi: AccountApi
    ): AccountRepository {
        return AccountRepositoryImpl(userSessionManager, accountApi)
    }

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }
}