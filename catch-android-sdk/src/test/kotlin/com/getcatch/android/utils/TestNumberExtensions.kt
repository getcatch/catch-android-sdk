package com.getcatch.android.utils

import org.junit.Test

public class TestNumberExtensions {

    @Test
    public fun `Test centsToDollarsString`() {
        assert(0.centsToDollarsString() == "$0.00")
        assert(12.centsToDollarsString() == "$0.12")
        assert(123.centsToDollarsString() == "$1.23")
        assert(1234.centsToDollarsString() == "$12.34")
        assert((-12).centsToDollarsString() == "-$0.12")
        assert((-123).centsToDollarsString() == "-$1.23")
        assert((-1234).centsToDollarsString() == "-$12.34")
    }
}
