package com.getcatch.android.utils

import androidx.compose.ui.graphics.Color

internal fun Color.Companion.fromHexString(hexString: String) =
    Color(android.graphics.Color.parseColor(hexString))
