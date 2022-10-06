package com.getcatch.android

import com.getcatch.android.test.rules.MainCoroutineRule
import org.junit.Rule
import org.junit.Test

public class CatchTest {

    @get:Rule
    internal val mainCoroutineRule = MainCoroutineRule()

    @Test
    public fun `Test greet initialization`() {
        Catch.initialize(publicKey = "test-public-key")
        Catch.greet()
    }
}
