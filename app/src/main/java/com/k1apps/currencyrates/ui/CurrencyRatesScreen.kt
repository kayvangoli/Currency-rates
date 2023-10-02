package com.k1apps.currencyrates.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.k1apps.currencyrates.R
import com.k1apps.currencyrates.ui.theme.CustomDimensions
import com.k1apps.currencyrates.ui.theme.CustomTypography
import com.k1apps.currencyrates.ui.theme.Gray
import com.k1apps.currencyrates.ui.theme.Gray2
import com.k1apps.currencyrates.ui.theme.Green
import com.k1apps.currencyrates.ui.theme.Red
import com.k1apps.currencyrates.viewmodel.CurrencyRateFlag
import com.k1apps.currencyrates.viewmodel.CurrencyRateViewData
import com.k1apps.currencyrates.viewmodel.CurrencyRatesViewModel

@Composable
fun CurrencyRatesScreen(viewModel: CurrencyRatesViewModel) {
    val currencyRates by viewModel.currencyRates.observeAsState(emptyList())
    val lastUpdateText by viewModel.updateDate.observeAsState("")
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadCurrencyRates()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(CustomDimensions.largePadding)
            .background(Color.White)
            .padding(CustomDimensions.largePadding)
    ) {
        Text(
            text = stringResource(id = R.string.rates),
            style = CustomTypography.satoshiExtraLargeBold,
            modifier = Modifier
                .align(Alignment.Start)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(currencyRates) { rateItem ->
                RateItemRow(rateItem)
                Spacer(modifier = Modifier.height(CustomDimensions.mediumPadding))
            }
        }

        Text(
            text = stringResource(id = R.string.last_updated, lastUpdateText),
            style = CustomTypography.satoshiMediumRegular,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Gray2
        )

        errorMessage?.let { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun RateItemRow(rateItem: CurrencyRateViewData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(CustomDimensions.largeRowHeight)
            .background(Gray, shape = RoundedCornerShape(CustomDimensions.normalRadius))
            .padding(CustomDimensions.largePadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CustomDimensions.mediumPadding)
        ) {

            Image(
                painter = painterResource(id = rateItem.imgRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(CustomDimensions.normalImageSize)
            )
            // Currency name
            Text(
                text = rateItem.name,
                style = CustomTypography.rowTextStyleBold
            )
        }

        Spacer(modifier = Modifier.width(CustomDimensions.largePadding))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CustomDimensions.smallPadding)
        ) {
            Text(
                text = rateItem.price,
                style = CustomTypography.rowTextStyleMedium,
                color = if (rateItem.currencyRateFlag == CurrencyRateFlag.UP) Green else Red
            )

            val arrowIcon =
                if (rateItem.currencyRateFlag == CurrencyRateFlag.UP)
                    R.drawable.ic_arrow_up
                else
                    R.drawable.ic_arrow_down
            Image(
                painter = painterResource(id = arrowIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(CustomDimensions.smallImageSize)
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
//    CurrencyRatesPage(CurrencyRatesViewModel(ServiceLocator.getUseCase()))
}