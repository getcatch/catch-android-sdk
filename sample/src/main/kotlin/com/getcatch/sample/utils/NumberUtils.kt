package com.getcatch.sample.utils

import java.text.NumberFormat
import java.util.*

private val dollarsFormatter = NumberFormat.getCurrencyInstance().apply {
    maximumFractionDigits = 0
    currency = Currency.getInstance("USD")
}

const val CENTS_PER_DOLLAR = 100f

fun centsToDollarsString(cents: Int): String {
    return dollarsFormatter.format(cents / CENTS_PER_DOLLAR)
}

data class EarnedAndDonatedRewards(val earned: Int, val donated: Int)

fun calculateEarnedAndDonatedRewards(price: Int, rewardsRate: Double): EarnedAndDonatedRewards {
    val totalRewards = (price * rewardsRate).toInt()
    val roundedToTheDollar = (totalRewards / CENTS_PER_DOLLAR).toInt() * CENTS_PER_DOLLAR.toInt()
    return EarnedAndDonatedRewards(
        earned = roundedToTheDollar,
        donated = totalRewards - roundedToTheDollar
    )
}
