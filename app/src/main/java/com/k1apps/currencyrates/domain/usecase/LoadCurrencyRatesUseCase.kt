package com.k1apps.currencyrates.domain.usecase

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.minutes

class LoadCurrencyRatesUseCase(private val repository: CurrencyRateRepository) {
    operator fun invoke(): Flow<Result<List<CurrencyRateData>>> = flow {
        while (currentCoroutineContext().isActive) {
            repository.getCurrencyRates()
            delay(2.minutes)
        }
    }
}