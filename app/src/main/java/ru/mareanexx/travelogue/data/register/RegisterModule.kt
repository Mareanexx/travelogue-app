package ru.mareanexx.travelogue.data.register

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.register.remote.api.RegisterApi
import ru.mareanexx.travelogue.data.register.repository.RegisterRepositoryImpl
import ru.mareanexx.travelogue.di.NetworkModule
import ru.mareanexx.travelogue.domain.register.RegisterRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RegisterModule {

    @Singleton
    @Provides
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(registerApi: RegisterApi): RegisterRepository {
        return RegisterRepositoryImpl(registerApi)
    }
}