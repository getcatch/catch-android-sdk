package com.getcatch.android.ui.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getcatch.android.ui.styles.values.ColorValue
import com.getcatch.android.ui.styles.values.FontWeight
import com.getcatch.android.ui.styles.values.TextTransform
import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

public data class ActionButtonStyle(
    val fontSize: Float? = null,
    val fontColor: ColorValue? = null,
    val height: Float? = null,
    val lineHeight: Float? = null,
    val fontWeight: FontWeight? = null,
    val letterSpacing: Float? = null,
    val elevation: Float? = null,
    val borderRadius: Float? = null,
    val backgroundColor: ColorValue? = null,
    val textTransform: TextTransform? = null,
) {
    internal fun withOverrides(overrides: ActionButtonStyle?): ActionButtonStyle {
        if (overrides == null) return this
        return ActionButtonStyle(
            fontSize = overrides.fontSize ?: fontSize,
            fontColor = overrides.fontColor ?: fontColor,
            height = overrides.height ?: height,
            lineHeight = overrides.lineHeight ?: lineHeight,
            fontWeight = overrides.fontWeight ?: fontWeight,
            letterSpacing = overrides.letterSpacing ?: letterSpacing,
            elevation = overrides.elevation ?: elevation,
            borderRadius = overrides.borderRadius ?: borderRadius,
            backgroundColor = overrides.backgroundColor ?: backgroundColor,
            textTransform = overrides.textTransform ?: textTransform,
        )
    }

    internal data class Resolved(
        val fontSize: TextUnit,
        val fontColor: Color,
        val height: Dp,
        val lineHeight: TextUnit,
        val fontWeight: ComposeFontWeight,
        val letterSpacing: TextUnit? = null,
        val elevation: Dp,
        val borderRadius: Dp,
        val backgroundColor: Color,
        val textTransform: TextTransform? = null,
    ) {
        fun withOverrides(overrides: ActionButtonStyle?): Resolved {
            if (overrides == null) return this
            return Resolved(
                fontSize = overrides.fontSize?.sp ?: fontSize,
                fontColor = overrides.fontColor?.value ?: fontColor,
                height = overrides.height?.dp ?: height,
                lineHeight = overrides.lineHeight?.sp ?: lineHeight,
                fontWeight = overrides.fontWeight?.toComposeFontWeight() ?: fontWeight,
                letterSpacing = overrides.letterSpacing?.sp ?: letterSpacing,
                elevation = overrides.elevation?.dp ?: elevation,
                borderRadius = overrides.borderRadius?.dp ?: borderRadius,
                backgroundColor = overrides.backgroundColor?.value ?: backgroundColor,
                textTransform = overrides.textTransform ?: textTransform,
            )
        }

        val composeTextStyle = TextStyle(
            color = fontColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing ?: TextUnit.Unspecified,
        )

        fun applyTextTransform(text: String) =
            textTransform?.transform(text) ?: text
    }
}
