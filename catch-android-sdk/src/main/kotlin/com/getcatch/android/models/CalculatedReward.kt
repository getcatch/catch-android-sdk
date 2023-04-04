package com.getcatch.android.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.getcatch.android.R
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.toPercentString

internal sealed class CalculatedReward {
    abstract val redeemable: Boolean
    abstract val formattedStringValue: String
    abstract val stringResId: Int

    class EarnedCredits(val amount: Int) : CalculatedReward() {
        override val redeemable: Boolean = false
        override val formattedStringValue: String = amount.centsToDollarsString()
        override val stringResId: Int = R.string.benefit_earn_message
    }

    class PercentRate(val percent: Double) : CalculatedReward() {
        override val redeemable: Boolean = false
        override val formattedStringValue: String = "${percent.toPercentString()}%"
        override val stringResId: Int = R.string.benefit_earn_message
    }

    class RedeemableCredits(val amount: Int) : CalculatedReward() {
        override val redeemable: Boolean = true
        override val formattedStringValue: String = amount.centsToDollarsString()
        override val stringResId: Int = R.string.benefit_redeem_message
    }

    class Saved(val amount: Int) : CalculatedReward() {
        override val redeemable: Boolean = true
        override val formattedStringValue: String = amount.centsToDollarsString()
        override val stringResId: Int = R.string.benefit_saved_message
    }

    @Composable
    fun getMessage(capitalize: Boolean = false): String {
        val message = stringResource(stringResId, formattedStringValue)
        if (capitalize) {
            return message.replaceFirstChar { it.uppercase() }
        }
        return message
    }

    companion object {
        // 10% default, only used when merchant fails to load
        private const val DEFAULT_REWARDS_RATE = 0.1
        val Default = PercentRate(DEFAULT_REWARDS_RATE)
    }
}
