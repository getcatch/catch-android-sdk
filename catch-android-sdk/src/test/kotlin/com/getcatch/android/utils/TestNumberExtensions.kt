package com.getcatch.android.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

public class TestNumberExtensions {

    @Test
    public fun `centsToDollarsString, test all cases`() {
        assertThat(0.centsToDollarsString()).isEqualTo("$0.00")
        assertThat(12.centsToDollarsString()).isEqualTo("$0.12")
        assertThat(123.centsToDollarsString()).isEqualTo("$1.23")
        assertThat(1234.centsToDollarsString()).isEqualTo("$12.34")
        assertThat((-12).centsToDollarsString()).isEqualTo("-$0.12")
        assertThat((-123).centsToDollarsString()).isEqualTo("-$1.23")
        assertThat((-1234).centsToDollarsString()).isEqualTo("-$12.34")
    }

    @Test
    public fun `toPercentString, all cases`() {
        assertThat(0.0.toPercentString()).isEqualTo("0")
        assertThat(0.5.toPercentString()).isEqualTo("50")
        assertThat(1.0.toPercentString()).isEqualTo("100")
        assertThat((-0.5).toPercentString()).isEqualTo("-50")
        assertThat((-1.0).toPercentString()).isEqualTo("-100")
    }
}
