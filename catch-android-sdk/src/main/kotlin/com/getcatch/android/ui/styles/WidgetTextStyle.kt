package com.getcatch.android.ui.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.getcatch.android.ui.styles.values.ColorValue
import com.getcatch.android.ui.styles.values.FontWeight
import com.getcatch.android.ui.styles.values.TextTransform
import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

/**
 * Styling for the text components within the Catch widgets.
 *
 * See [`WidgetTextStyle.create`](WidgetTextStyle.create) for apps that do not have Compose
 * dependencies used in the primary constructor.
 */
public data class WidgetTextStyle(
    /** Configures the font size for text components. */
    val fontSize: Float? = null,

    /** Configures the font color for text components. */
    val fontColor: Color? = null,

    /** Configures the font weight for text components. */
    val fontWeight: ComposeFontWeight? = null,

    /** Configures the line height for text components. */
    val lineHeight: Float? = null,

    /** Configures the letter spacing for text components. */
    val letterSpacing: Float? = null,

    /** Transforms casing of text in text components. */
    val textTransform: TextTransform? = null,
) {

    internal fun withOverrides(overrides: WidgetTextStyle?): WidgetTextStyle {
        if (overrides == null) return this
        return WidgetTextStyle(
            fontSize = overrides.fontSize ?: fontSize,
            fontColor = overrides.fontColor ?: fontColor,
            fontWeight = overrides.fontWeight ?: fontWeight,
            lineHeight = overrides.lineHeight ?: lineHeight,
            letterSpacing = overrides.letterSpacing ?: letterSpacing,
            textTransform = overrides.textTransform ?: textTransform,
        )
    }

    internal data class Resolved(
        val fontSize: TextUnit,
        val fontColor: Color,
        val fontWeight: ComposeFontWeight,
        val lineHeight: TextUnit,
        val letterSpacing: TextUnit? = null,
        val textTransform: TextTransform? = null,
    ) {
        fun withOverrides(overrides: WidgetTextStyle?): Resolved {
            if (overrides == null) return this
            return Resolved(
                fontSize = overrides.fontSize?.sp ?: fontSize,
                fontColor = overrides.fontColor ?: fontColor,
                fontWeight = overrides.fontWeight ?: fontWeight,
                lineHeight = overrides.lineHeight?.sp ?: lineHeight,
                letterSpacing = overrides.letterSpacing?.sp ?: letterSpacing,
                textTransform = overrides.textTransform ?: textTransform,
            )
        }
    }

    public companion object {
        /**
         * Utility function to replace primary constructor for view based apps that use the SDK
         * provided [`ColorValue`](ColorValue) and [`FontWeight`](FontWeight) instead of the Compose
         * [`Color`](androidx.compose.ui.graphics.Color) and
         * [`FontWeight`](androidx.compose.ui.text.font.FontWeight).
         */
        public fun create(
            fontSize: Float? = null,
            fontColor: ColorValue? = null,
            fontWeight: FontWeight? = null,
            lineHeight: Float? = null,
            letterSpacing: Float? = null,
            textTransform: TextTransform? = null,
        ): WidgetTextStyle = WidgetTextStyle(
            fontSize = fontSize,
            fontColor = fontColor?.value,
            fontWeight = fontWeight?.toComposeFontWeight(),
            lineHeight = lineHeight,
            letterSpacing = letterSpacing,
            textTransform = textTransform,
        )
    }
}
