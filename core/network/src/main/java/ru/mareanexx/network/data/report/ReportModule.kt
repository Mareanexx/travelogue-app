package ru.mareanexx.network.data.report

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.network.data.report.remote.api.ReportApi
import ru.mareanexx.network.data.report.repository.ReportRepositoryImpl
import ru.mareanexx.network.domain.report.ReportRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReportModule {
    @Singleton
    @Provides
    fun provideReportRepository(reportApi: ReportApi): ReportRepository {
        return ReportRepositoryImpl(reportApi)
    }

    @Singleton
    @Provides
    fun provideReportApi(retrofit: Retrofit): ReportApi {
        return retrofit.create(ReportApi::class.java)
    }
}