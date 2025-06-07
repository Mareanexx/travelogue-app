package ru.mareanexx.feature_auth.data.register

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.network.di.NetworkModule
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_auth.data.register.remote.api.RegisterApi
import ru.mareanexx.feature_auth.data.register.repository.RegisterRepositoryImpl
import ru.mareanexx.feature_auth.domain.register.RegisterRepository
import javax.inject.Singleton

@Module(includes = [ru.mareanexx.network.di.NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RegisterModule {

    @Singleton
    @Provides
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(
        userSessionManager: UserSessionManager,
        registerApi: RegisterApi
    ): RegisterRepository {
        return RegisterRepositoryImpl(userSessionManager, registerApi)
    }
}