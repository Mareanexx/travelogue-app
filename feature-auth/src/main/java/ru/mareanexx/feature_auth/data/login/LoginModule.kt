package ru.mareanexx.feature_auth.data.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.network.di.NetworkModule
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_auth.data.login.remote.api.LoginApi
import ru.mareanexx.feature_auth.data.login.repository.LoginRepositoryImpl
import ru.mareanexx.feature_auth.domain.login.LoginRepository
import javax.inject.Singleton

@Module(includes = [ru.mareanexx.network.di.NetworkModule::class])
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(
        userSessionManager: UserSessionManager,
        loginApi: LoginApi
    ): LoginRepository {
        return LoginRepositoryImpl(userSessionManager, loginApi)
    }
}