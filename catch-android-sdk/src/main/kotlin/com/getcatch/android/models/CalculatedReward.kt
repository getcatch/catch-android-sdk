package com.getcatch.android.models

import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.toPercentString

internal sealed interface CalculatedReward {
    val redeemable: Boolean
    val formattedStringValue: String

    class EarnedCredits(amount: Int): CalculatedReward {
        override val redeemable: Boolean = false
        override val formattedStringValue: String = amount.centsToDollarsString()
    }

    class RedeemableCredits(amount: Int): CalculatedReward {
        override val redeemable: Boolean = true
        override val formattedStringValue: String = amount.centsToDollarsString()
    }

    class PercentRate(percent: Double): CalculatedReward {
        override val redeemable: Boolean = false
        override val formattedStringValue: String = "${percent.toPercentString()}%"
    }

    companion object {
        // 10% default, only used when merchant fails to load
        private const val DEFAULT_REWARDS_RATE = 0.1
        val Default = PercentRate(DEFAULT_REWARDS_RATE)
    }
}
