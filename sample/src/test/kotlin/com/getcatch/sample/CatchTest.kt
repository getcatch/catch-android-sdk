package com.getcatch.sample

import com.getcatch.android.Catch
import com.getcatch.android.exceptions.CatchNotInitializedException
import com.getcatch.sample.test.rules.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class CatchTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun greet() {
        Assert.assertThrows(CatchNotInitializedException::class.java) {
            Catch.greet()
        }
        Catch.initialize("test-public-key")
        assertThat(
            Catch.greet()
        ).isEqualTo("Hello from Catch!")
    }
}
