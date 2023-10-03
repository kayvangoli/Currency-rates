package com.k1apps.currencyrates.di

import com.k1apps.currencyrates.domain.usecase.ConnectionManager
import com.k1apps.currencyrates.domain.usecase.CurrencyRateRepository
import com.k1apps.currencyrates.infrustructure.remote.DefaultConnectionManager
import com.k1apps.currencyrates.infrustructure.remote.DefaultCurrencyRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    abstract fun bindCurrencyRatesRepo(
        defaultCurrencyRateRepository: DefaultCurrencyRateRepository
    ): CurrencyRateRepository
    @Binds
    abstract fun bindConnectionManager(
        defaultConnectionManager: DefaultConnectionManager
    ): ConnectionManager
}