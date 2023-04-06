package com.getcatch.android.utils

import androidx.compose.ui.text.AnnotatedString
import com.getcatch.android.ui.styles.values.TextTransform

internal fun AnnotatedString.Builder.transformAndAppend(textTransform: TextTransform?, string: String) {
    append(textTransform?.transform(string) ?: string)
}
