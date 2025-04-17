package ru.mareanexx.travelogue.data.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.login.remote.api.LoginApi
import ru.mareanexx.travelogue.data.login.repository.LoginRepositoryImpl
import ru.mareanexx.travelogue.di.NetworkModule
import ru.mareanexx.travelogue.domain.login.LoginRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {

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