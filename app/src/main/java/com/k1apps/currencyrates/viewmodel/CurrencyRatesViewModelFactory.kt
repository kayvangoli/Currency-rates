package com.k1apps.currencyrates.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.k1apps.currencyrates.domain.usecase.LoadCurrencyRatesUseCase

class CurrencyRatesViewModelFactory(
    private val currencyRatesUseCase: LoadCurrencyRatesUseCase,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyRatesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyRatesViewModel( context, currencyRatesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}