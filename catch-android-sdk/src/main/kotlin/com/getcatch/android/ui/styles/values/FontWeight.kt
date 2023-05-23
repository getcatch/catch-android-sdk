package com.getcatch.android.ui.styles.values

import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

/**
 * An enum of the font weights that can be used in custom styling.
 *
 * If you load a custom font via [`Catch.initialize`](com.getcatch.android.Catch.initialize) or
 * [`Catch.setCustomFontFamily`](com.getcatch.android.Catch.setCustomFontFamily) then make sure you
 * provide a font resource for each font weight you intend to use.
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
    W900,

    /** W100 */
    THIN,

    /** W200 */
    EXTRA_LIGHT,

    /** W300 */
    LIGHT,

    /** W400 */
    NORMAL,

    /** W500 */
    MEDIUM,

    /** W600 */
    SEMI_BOLD,

    /** W700 */
    BOLD,

    /** W800 */
    EXTRA_BOLD,

    /** W900 */
    BLACK;

    internal fun toComposeFontWeight(): ComposeFontWeight = when (this) {
        W100, THIN -> ComposeFontWeight.W100
        W200, EXTRA_LIGHT -> ComposeFontWeight.W200
        W300, LIGHT -> ComposeFontWeight.W300
        W400, NORMAL -> ComposeFontWeight.W400
        W500, MEDIUM -> ComposeFontWeight.W500
        W600, SEMI_BOLD -> ComposeFontWeight.W600
        W700, BOLD -> ComposeFontWeight.W700
        W800, EXTRA_BOLD -> ComposeFontWeight.W800
        W900, BLACK -> ComposeFontWeight.W900
    }
}
