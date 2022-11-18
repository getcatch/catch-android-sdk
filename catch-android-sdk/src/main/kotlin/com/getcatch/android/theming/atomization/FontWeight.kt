package com.getcatch.android.theming.atomization

import androidx.compose.ui.text.font.FontWeight

public enum class FontWeight {
    W400,
    W500,
    W700;

    internal fun toComposeFontWeight(): FontWeight = when (this) {
        W400 -> FontWeight.W400
        W500 -> FontWeight.W500
        W700 -> FontWeight.W700
    }
}
