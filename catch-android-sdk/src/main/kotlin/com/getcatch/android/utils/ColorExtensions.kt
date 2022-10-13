package com.getcatch.android.utils

import android.util.Log
import androidx.compose.ui.graphics.Color

internal fun Color.Companion.fromHexString(hexString: String?, default: Color): Color {
    if (hexString == null) {
        return default
    }
    return try {
        Color(android.graphics.Color.parseColor(hexString))
    } catch (ex: IllegalArgumentException) {
        Log.w("Color.fromHexString", "Cannot convert the string to a color: $hexString", ex)
        default
    }
}
