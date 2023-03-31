package com.getcatch.android.models

import com.google.common.truth.Truth.assertThat
import org.junit.Test

public class TestCalculatedReward {
    @Test
    public fun `EarnedCredits, formats value properly`() {
        val oneDollar = CalculatedReward.EarnedCredits(100)
        assertThat(oneDollar.formattedStringValue).isEqualTo("$1.00")
    }

    @Test
    public fun `RedeemableCredits, formats value properly`() {
        val oneDollar = CalculatedReward.RedeemableCredits(100)
        assertThat(oneDollar.formattedStringValue).isEqualTo("$1.00")
    }

    @Test
    public fun `PercentRate, formats value properly`() {
        val oneDollar = CalculatedReward.PercentRate(0.1)
        assertThat(oneDollar.formattedStringValue).isEqualTo("10%")
    }
}
