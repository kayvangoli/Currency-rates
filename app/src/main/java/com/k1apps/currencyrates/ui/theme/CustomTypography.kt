package com.k1apps.currencyrates.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.k1apps.currencyrates.R

object CustomTypography {
    val satoshiExtraLargeBold = TextStyle(
        fontFamily = satoshiFont,
        fontSize = CustomDimensions.extraLargeFontSize
    )
    val rowTextStyleBold = TextStyle(
        fontFamily = satoshiFont,
        fontSize = CustomDimensions.largeFontSize
    )
    val rowTextStyleMedium = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.satoshi_medium)
        ),
        fontSize = CustomDimensions.largeFontSize
    )
    val satoshiMediumRegular = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.satoshi_regular)
        ),
        fontSize = CustomDimensions.mediumFontSize
    )
}