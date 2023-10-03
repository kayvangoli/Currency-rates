package com.k1apps.currencyrates.viewmodel

data class CurrencyRateViewData(
    val id: String,
    val name: String,
    val imgRes: Int,
    val price: String,
    val currencyRateFlag: CurrencyRateFlag
)

enum class CurrencyRateFlag{
    UP, DOWN
}
