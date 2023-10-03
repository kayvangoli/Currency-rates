package com.k1apps.currencyrates.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.k1apps.currencyrates.domain.usecase.CurrencyRateRepository
import com.k1apps.currencyrates.domain.usecase.LoadCurrencyRatesUseCase
import com.k1apps.currencyrates.infrustructure.remote.CurrencyRateApi
import com.k1apps.currencyrates.infrustructure.remote.DefaultConnectionManager
import com.k1apps.currencyrates.infrustructure.remote.DefaultCurrencyRateRepository
import com.k1apps.currencyrates.viewmodel.CurrencyRatesViewModel
import com.k1apps.currencyrates.viewmodel.CurrencyRatesViewModelFactory
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

    private fun getRepository(): CurrencyRateRepository {
        return DefaultCurrencyRateRepository(getRetrofit().create(CurrencyRateApi::class.java))
    }

    private fun getUseCase(context: Context): LoadCurrencyRatesUseCase =
        LoadCurrencyRatesUseCase(
            getRepository(),
            DefaultConnectionManager(context)
        )

//    fun getCurrencyRatesViewModel(context: Context, viewModelStore: ViewModelStore): CurrencyRatesViewModel =
//        ViewModelProvider(
//            viewModelStore, CurrencyRatesViewModelFactory(getUseCase(context), context = context)
//        )[CurrencyRatesViewModel::class.java]
}