package com.k1apps.currencyrates.infrustructure.remote

import com.k1apps.currencyrates.domain.usecase.ConnectionManager

class DefaultConnectionManager : ConnectionManager {
    override fun isOnline(): Boolean {
        return true
    }

}