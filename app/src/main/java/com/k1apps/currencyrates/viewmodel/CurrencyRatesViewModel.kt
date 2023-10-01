package com.k1apps.currencyrates.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k1apps.currencyrates.domain.usecase.LoadCurrencyRatesUseCase
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import java.util.Date

class CurrencyRatesViewModel(
    private val currencyRatesUseCase: LoadCurrencyRatesUseCase
) : ViewModel() {

    private val _currencyRate = MutableLiveData<List<CurrencyRateViewData>>()
    val currencyRates: LiveData<List<CurrencyRateViewData>> = _currencyRate
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _updateDate = MutableLiveData<String>()
    val updateDate : LiveData<String> = _updateDate
    fun loadCurrencyRates() {
        viewModelScope.launch {
            currencyRatesUseCase().collect { result ->
                if (result.isFailure) {
                    _errorMessage.value = result.exceptionOrNull()!!.message
                } else {
                    _currencyRate.value = result.getOrNull()!!.map { currencyRateData ->
                        val oldItem = _currencyRate.value?.find { currencyRateViewData ->
                            currencyRateData.symbol == currencyRateViewData.name
                        }
                        val flag = if (oldItem == null) {
                            CurrencyRateFlag.UP
                        } else if (oldItem.price <= currencyRateData.price) {
                            CurrencyRateFlag.UP
                        } else {
                            CurrencyRateFlag.DOWN
                        }
                        currencyRateData.toCurrencyRateViewData(flag)
                    }
                    _updateDate.value = formatDateToCustomFormat(Date())
                }
            }
        }
    }
    @TestOnly
    fun setFakeCurrencyRates(fakeValues: List<CurrencyRateViewData>) {
        _currencyRate.value = fakeValues
    }
}
