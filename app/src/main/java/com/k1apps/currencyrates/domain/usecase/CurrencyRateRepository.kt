package com.k1apps.currencyrates.domain.usecase

interface CurrencyRateRepository {
    fun getCurrencyRates(): Result<List<CurrencyRateData>>
}