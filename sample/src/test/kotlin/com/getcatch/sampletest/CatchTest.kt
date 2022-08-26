package com.getcatch.sampletest

import com.getcatch.android.Catch
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CatchTest {

    @Test
    fun greet() {
        assertThat(
            Catch.greet()
        ).isEqualTo("Hello from Catch!")
    }
}