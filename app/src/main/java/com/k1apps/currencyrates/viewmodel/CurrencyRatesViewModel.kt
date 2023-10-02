package com.k1apps.currencyrates.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k1apps.currencyrates.domain.usecase.LoadCurrencyRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import java.text.DecimalFormat
import java.util.Date
import java.util.Locale

@SuppressLint("StaticFieldLeak")
class CurrencyRatesViewModel(
     private val context: Context,
    private val currencyRatesUseCase: LoadCurrencyRatesUseCase
) : ViewModel() {

    private val _currencyRate = MutableLiveData<List<CurrencyRateViewData>>()
    val currencyRates: LiveData<List<CurrencyRateViewData>> = _currencyRate
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _updateDate = MutableLiveData<String>()
    val updateDate : LiveData<String> = _updateDate
    fun loadCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRatesUseCase().collect { result ->
                if (result.isFailure) {
                    _errorMessage.postValue(result.exceptionOrNull()!!.message)
                } else {
                    val currencyRates = result.getOrNull()!!.map { currencyRateData ->
                        val oldItem = _currencyRate.value?.find { currencyRateViewData ->
                            currencyRateData.symbol == currencyRateViewData.name
                        }
                        val flag = if (oldItem == null) {
                            CurrencyRateFlag.UP
                        } else if (oldItem.price.toDouble() <= currencyRateData.price) {
                            CurrencyRateFlag.UP
                        } else {
                            CurrencyRateFlag.DOWN
                        }
                        val df = DecimalFormat("#.####")
                        val price = df.format(currencyRateData.price)
                        val name =
                            currencyRateData.symbol.insertCharacterAtIndex(3, '/')
                        val imgRes = getDrawableResourceId(
                            currencyRateData.symbol.lowercase(
                                Locale.getDefault()
                            )
                        )
                        CurrencyRateViewData(name, imgRes, price, flag)
                    }
                    _currencyRate.postValue(currencyRates)
                    _updateDate.postValue(formatDateToCustomFormat(Date()))
                }
            }
        }

    }
    @SuppressLint("DiscouragedApi")
    private fun getDrawableResourceId(drawableName: String): Int {
        return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }
    @TestOnly
    fun setFakeCurrencyRates(fakeValues: List<CurrencyRateViewData>) {
        _currencyRate.value = fakeValues
    }
}
