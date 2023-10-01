package com.k1apps.currencyrates.viewmodel

data class CurrencyRateViewData(
    val name: String,
    val imgRes: Int,
    val price: Double,
    val currencyRateFlag: CurrencyRateFlag
)

enum class CurrencyRateFlag{
    UP, DOWN
}
