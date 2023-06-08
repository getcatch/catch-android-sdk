package com.getcatch.android.utils

import androidx.compose.ui.graphics.Color
import timber.log.Timber

internal fun Color.Companion.fromHexString(hexString: String) =
    Color(android.graphics.Color.parseColor(hexString))

internal fun Color.Companion.fromHexString(hexString: String?, default: Color): Color {
    if (hexString == null) {
        return default
    }
    return try {
        Color(android.graphics.Color.parseColor(hexString))
    } catch (ex: IllegalArgumentException) {
        Timber.w(t = ex, message = "Cannot convert the string to a color: $hexString")
        default
    }
}
