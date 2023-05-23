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

/**
 * The styling for action buttons found in Catch widgets.
 *
 * The following widgets contain action buttons:
 * - [`PurchaseConfirmation`](com.getcatch.android.ui.composables.PurchaseConfirmation)
 * - [`CampaignLink`](com.getcatch.android.ui.composables.CampaignLink)
 *
 * See [`ActionButtonStyle.create`](ActionButtonStyle.create) for apps that do not have Compose
 * dependencies used in the primary constructor.
 */
public data class ActionButtonStyle(
    /** Configures the font size for the button's label. */
    val fontSize: Float? = null,

    /** Configures the font color for the button's label. */
    val fontColor: Color? = null,

    /** Configures the button's height. */
    val height: Float? = null,

    /** Configures the line height for the button's label. */
    val lineHeight: Float? = null,

    /** Configures the font weight for the button's label. */
    val fontWeight: ComposeFontWeight? = null,

    /** Configures the letter spacing for the button's label. */
    val letterSpacing: Float? = null,

    /**
     * Configures the elevation of the button which controls the shadow.
     *
     * For details on elevation and shadows in Android, please refer to
     * the [official Android docs](https://developer.android.com/develop/ui/views/theming/shadows-clipping).
     */
    val elevation: Float? = null,

    /** Configures the border radius of the button. */
    val borderRadius: Float? = null,

    /** Configures the background color of the button. */
    val backgroundColor: Color? = null,

    /** Transforms casing of text for the button's label. */
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
                fontColor = overrides.fontColor ?: fontColor,
                height = overrides.height?.dp ?: height,
                lineHeight = overrides.lineHeight?.sp ?: lineHeight,
                fontWeight = overrides.fontWeight ?: fontWeight,
                letterSpacing = overrides.letterSpacing?.sp ?: letterSpacing,
                elevation = overrides.elevation?.dp ?: elevation,
                borderRadius = overrides.borderRadius?.dp ?: borderRadius,
                backgroundColor = overrides.backgroundColor ?: backgroundColor,
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
            height: Float? = null,
            lineHeight: Float? = null,
            fontWeight: FontWeight? = null,
            letterSpacing: Float? = null,
            elevation: Float? = null,
            borderRadius: Float? = null,
            backgroundColor: ColorValue? = null,
            textTransform: TextTransform? = null,
        ): ActionButtonStyle = ActionButtonStyle(
            fontSize = fontSize,
            fontColor = fontColor?.value,
            height = height,
            lineHeight = lineHeight,
            fontWeight = fontWeight?.toComposeFontWeight(),
            letterSpacing = letterSpacing,
            elevation = elevation,
            borderRadius = borderRadius,
            backgroundColor = backgroundColor?.value,
            textTransform = textTransform,
        )
    }
}
