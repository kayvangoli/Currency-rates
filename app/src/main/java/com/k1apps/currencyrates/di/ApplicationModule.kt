package com.k1apps.currencyrates.di

import com.k1apps.currencyrates.domain.usecase.ConnectionManager
import com.k1apps.currencyrates.domain.usecase.CurrencyRateRepository
import com.k1apps.currencyrates.domain.usecase.LoadCurrencyRatesUseCase
import com.k1apps.currencyrates.infrustructure.remote.CurrencyRateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun provideLoadCurrencyRatesUseCase(
        repository: CurrencyRateRepository,
        connectionManager: ConnectionManager
    ): LoadCurrencyRatesUseCase {
        return LoadCurrencyRatesUseCase(repository, connectionManager)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val builder = Retrofit.Builder()
        return builder.baseUrl("https://lokomond.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyRatesApi(
        retrofit: Retrofit
    ): CurrencyRateApi =
        retrofit.create(CurrencyRateApi::class.java)


}