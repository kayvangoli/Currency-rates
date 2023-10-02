package com.k1apps.currencyrates.infrustructure.remote

import android.content.Context
import android.net.ConnectivityManager
import com.k1apps.currencyrates.domain.usecase.ConnectionManager


@Suppress("DEPRECATION")
class DefaultConnectionManager(
    private val context: Context
) : ConnectionManager {
    override fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}