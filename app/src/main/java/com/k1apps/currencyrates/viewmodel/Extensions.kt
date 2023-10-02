package com.k1apps.currencyrates.viewmodel

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateToCustomFormat(date: Date): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
    return sdf.format(date)
}

fun String.insertCharacterAtIndex(index: Int, character: Char): String {
    if (index < 0 || index > length) {
        throw IllegalArgumentException("Index is out of bounds")
    }
    val firstPart = substring(0, index)
    val secondPart = substring(index)
    return "$firstPart$character$secondPart"
}