package com.getcatch.android.utils

import java.text.NumberFormat
import java.util.Currency
import kotlin.math.roundToInt

private val dollarsFormatter = NumberFormat.getCurrencyInstance().apply {
    maximumFractionDigits = 2
    currency = Currency.getInstance("USD")
}

private const val CENTS_PER_DOLLAR = 100f
private const val PERCENT_MULTIPLIER = 100

internal fun Int.centsToDollarsString(): String {
    return dollarsFormatter.format(this / CENTS_PER_DOLLAR)
}

internal fun Float.toPercentString() = "${(this * PERCENT_MULTIPLIER).roundToInt()}"
