package com.k1apps.currencyrates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.k1apps.currencyrates.di.ServiceLocator
import com.k1apps.currencyrates.ui.CurrencyRatesScreen
import com.k1apps.currencyrates.ui.theme.CurrencyRatesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ServiceLocator.getCurrencyRatesViewModel(applicationContext, viewModelStore)
        setContent {
            CurrencyRatesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyRatesScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}