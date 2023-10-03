package com.k1apps.currencyrates.domain.usecase

class CurrencyRatesException(val error: Error) : Exception(error.toString()) {

    enum class Error(private val description: String = "") {
        INVALID_DATA("invalid-data"),
        NO_CONNECTIVITY("no-connectivity");

        override fun toString(): String = description
    }
}