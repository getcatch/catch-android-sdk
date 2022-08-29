package com.getcatch.android.util

import java.text.NumberFormat
import java.util.Currency

private val dollarsFormatter = NumberFormat.getCurrencyInstance().apply {
    maximumFractionDigits = 2
    currency = Currency.getInstance("USD")
}

private const val CENTS_PER_DOLLAR = 100f

internal fun Int.centsToDollarsString(): String {
    return dollarsFormatter.format(this / CENTS_PER_DOLLAR)
}
