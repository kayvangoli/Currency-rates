package com.k1apps.currencyrates.infrustructure.remote

import com.k1apps.currencyrates.domain.usecase.CurrencyRateData
import com.k1apps.currencyrates.domain.usecase.CurrencyRateRepository
import javax.inject.Inject

class DefaultCurrencyRateRepository @Inject constructor(
    private val api: CurrencyRateApi
) : CurrencyRateRepository {
    override fun getCurrencyRates(): Result<List<CurrencyRateData>> {
        return try {
            val body = api.getRates().execute().body()
            if (body == null) Result.failure(RepositoryException(NullPointerException()))
            else Result.success(body.rates.map {
                CurrencyRateData(it.symbol, it.price)
            })
        } catch (e: Exception) {
            Result.failure(RepositoryException(e))
        }
    }
}