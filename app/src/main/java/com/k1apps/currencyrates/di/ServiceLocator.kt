package com.k1apps.currencyrates.di

import com.k1apps.currencyrates.domain.usecase.CurrencyRateRepository
import com.k1apps.currencyrates.infrustructure.remote.CurrencyRateApi
import com.k1apps.currencyrates.infrustructure.remote.DefaultCurrencyRateRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {
    private fun getRetrofit(): Retrofit {
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

    fun getRepository(): CurrencyRateRepository {
        return DefaultCurrencyRateRepository(getRetrofit().create(CurrencyRateApi::class.java))
    }
}