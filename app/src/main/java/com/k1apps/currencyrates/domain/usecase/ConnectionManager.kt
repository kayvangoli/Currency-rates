package com.k1apps.currencyrates.domain.usecase

interface ConnectionManager {
    fun isOnline(): Boolean
}
