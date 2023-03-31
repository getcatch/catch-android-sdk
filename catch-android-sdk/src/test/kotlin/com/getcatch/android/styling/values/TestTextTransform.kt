package com.getcatch.android.styling.values

import com.google.common.truth.Truth.assertThat
import org.junit.Test

public class TestTextTransform {

    @Test
    public fun `transform, all cases`() {
        assertThat(TextTransform.CAPITALIZE.transform("test string")).isEqualTo("Test String")
        assertThat(TextTransform.UPPERCASE.transform("test string")).isEqualTo("TEST STRING")
        assertThat(TextTransform.LOWERCASE.transform("TEST STRING")).isEqualTo("test string")
    }
}
