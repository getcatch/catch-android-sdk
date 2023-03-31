package com.getcatch.android.styling.values

import com.getcatch.android.utils.capitalizeWords

public enum class TextTransform {
    CAPITALIZE,
    UPPERCASE,
    LOWERCASE;

    internal fun transform(string: String) : String  = when(this) {
        CAPITALIZE -> string.capitalizeWords()
        UPPERCASE -> string.uppercase()
        LOWERCASE -> string.lowercase()
    }
}
