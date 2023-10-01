package com.k1apps.currencyrates.domain.usecase

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.minutes

class LoadCurrencyRatesUseCase(
    private val repository: CurrencyRateRepository,
    private val connectionManager: ConnectionManager
) {
    operator fun invoke(): Flow<Result<List<CurrencyRateData>>> = flow {
        while (currentCoroutineContext().isActive) {
            if (connectionManager.isOnline().not()) {
                emit(getErrorResult(CurrencyRatesException.Error.NO_CONNECTIVITY))
            } else {
                val repoResult = repository.getCurrencyRates()
                if (isResultValid(repoResult)) {
                    emit(Result.success(arrayListOf()))
                } else {
                    emit(getErrorResult(CurrencyRatesException.Error.INVALID_DATA))
                }
            }
            delay(2.minutes)
        }
    }

    private fun isResultValid(result: Result<List<CurrencyRateData>>) =
        result.getOrNull()?.isNotEmpty() ?: false

    private fun getErrorResult(error: CurrencyRatesException.Error) =
        Result.failure<List<CurrencyRateData>>(CurrencyRatesException(error))
}