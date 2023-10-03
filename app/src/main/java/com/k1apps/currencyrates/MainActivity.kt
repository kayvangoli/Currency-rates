package com.k1apps.currencyrates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.k1apps.currencyrates.ui.CurrencyRatesScreen
import com.k1apps.currencyrates.ui.theme.CurrencyRatesTheme
import com.k1apps.currencyrates.viewmodel.CurrencyRatesViewModel
import com.k1apps.currencyrates.viewmodel.CurrencyRatesViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: CurrencyRatesViewModelFactory

    private val viewModel: CurrencyRatesViewModel by viewModels {
        factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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