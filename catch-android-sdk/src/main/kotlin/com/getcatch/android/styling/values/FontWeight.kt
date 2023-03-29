package com.getcatch.android.styling.values

import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

public enum class FontWeight {
    W100,
    W200,
    W300,
    W400,
    W500,
    W600,
    W700,
    W800,
    W900;

    internal fun toComposeFontWeight(): ComposeFontWeight = when (this) {
        W100 -> ComposeFontWeight.W100
        W200 -> ComposeFontWeight.W200
        W300 -> ComposeFontWeight.W300
        W400 -> ComposeFontWeight.W400
        W500 -> ComposeFontWeight.W500
        W600 -> ComposeFontWeight.W600
        W700 -> ComposeFontWeight.W700
        W800 -> ComposeFontWeight.W800
        W900 -> ComposeFontWeight.W900
    }
}
