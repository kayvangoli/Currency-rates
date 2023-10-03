package com.k1apps.currencyrates.infrustructure.remote

import android.content.Context
import android.net.ConnectivityManager
import com.k1apps.currencyrates.domain.usecase.ConnectionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@Suppress("DEPRECATION")
class DefaultConnectionManager @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectionManager {
    override fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}