package com.getcatch.android.ui.styles.values

import com.getcatch.android.utils.capitalizeWords

/**
 * Options to control the casing of text in widgets.
 */
public enum class TextTransform {
    /** Capitalizes each word in the string (ex. "Test String"). */
    CAPITALIZE,

    /** Converts each character in the string to uppercase (ex. "test string"). */
    UPPERCASE,

    /** Converts each character in the string to lowercase (ex. "test string"). */
    LOWERCASE;

    internal fun transform(string: String): String = when (this) {
        CAPITALIZE -> string.capitalizeWords()
        UPPERCASE -> string.uppercase()
        LOWERCASE -> string.lowercase()
    }
}
