package com.k1apps.currencyrates.viewmodel

import android.icu.text.SimpleDateFormat
import com.k1apps.currencyrates.domain.usecase.CurrencyRateData
import java.util.Date
import java.util.Locale

fun CurrencyRateData.toCurrencyRateViewData(currencyRateFlag: CurrencyRateFlag)
        : CurrencyRateViewData {
    return CurrencyRateViewData(symbol, 0, price, currencyRateFlag)
}

fun formatDateToCustomFormat(date: Date): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
    return sdf.format(date)
}