package com.getcatch.android.ui.styles.values

import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

/**
 * An enum of the font weights that can be used in custom styling.
 *
 * If you load a custom font via [Catch.setCustomFontFamily] then make sure you provide
 * a font resource for each font weight you intend to use.
 */
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
