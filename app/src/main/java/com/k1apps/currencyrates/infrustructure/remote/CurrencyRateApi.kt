package com.k1apps.currencyrates.infrustructure.remote

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyRateApi {
    @GET("code-challenge/index.php")
    fun getRates(): Call<RatesEntity>
}