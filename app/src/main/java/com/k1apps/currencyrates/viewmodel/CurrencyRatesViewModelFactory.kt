package com.k1apps.currencyrates.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.k1apps.currencyrates.domain.usecase.LoadCurrencyRatesUseCase
import javax.inject.Inject

class CurrencyRatesViewModelFactory @Inject constructor(
    private val currencyRatesUseCase: LoadCurrencyRatesUseCase,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyRatesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyRatesViewModel( application, currencyRatesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}