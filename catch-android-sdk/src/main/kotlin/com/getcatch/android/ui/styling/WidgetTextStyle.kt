package com.getcatch.android.ui.styling

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.getcatch.android.styling.values.ColorValue
import com.getcatch.android.styling.values.FontWeight
import com.getcatch.android.styling.values.TextTransform
import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

public data class WidgetTextStyle(
    val fontSize: Float? = null,
    val fontColor: ColorValue? = null,
    val fontWeight: FontWeight? = null,
    val lineHeight: Float? = null,
    val letterSpacing: Float? = null,
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
                fontColor = overrides.fontColor?.value ?: fontColor,
                fontWeight = overrides.fontWeight?.toComposeFontWeight() ?: fontWeight,
                lineHeight = overrides.lineHeight?.sp ?: lineHeight,
                letterSpacing = overrides.letterSpacing?.sp,
                textTransform = overrides.textTransform,
            )
        }
    }
}
