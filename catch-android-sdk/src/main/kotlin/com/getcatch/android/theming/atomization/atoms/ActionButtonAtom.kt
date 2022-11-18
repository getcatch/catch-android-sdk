package com.getcatch.android.theming.atomization.atoms

import androidx.compose.ui.graphics.Color
import com.getcatch.android.theming.ThemeConfig
import com.getcatch.android.theming.atomization.FontWeight
import com.getcatch.android.theming.atomization.TextTransform

/**
 * The action button is a button that communicates the primary action of a widget.
 *
 * The following widgets include the action button atom:
 *   - Purchase Confirmation
 *   - Campaign Link
 */
public data class ActionButtonAtom(
    /**
     * Sets the font-size of the action button text.
     */
    val fontSize: Int? = null,

    /**
     * Sets the color of the action button text.
     */
    val fontColor: Color? = null,

    /**
     * Sets the height of the action button.
     */
    val height: Int? = null,

    /**
     * Sets the line height of the action button.
     */
    val lineHeight: Int? = null,

    /**
     * Sets the font weight of the action button text.
     */
    val fontWeight: FontWeight? = null,

    /**
     * Sets the letter spacing of the action button text.
     */
    val letterSpacing: Float? = null,

//    /**
//     * Sets the box shadow of the action button.
//     */
//    val boxShadow: Int? = null,

    /**
     * Sets the border radius of the action button.
     */
    val borderRadius: Int? = null,

    /**
     * Sets the background color of the action button.
     */
    val backgroundColor: Color? = null,

    /**
     * Sets the text transform of the action button text.
     */
    val textTransform: TextTransform? = null,
) {
    internal data class Resolved(
        val fontSize: Int,
        val fontColor: Color,
        val height: Int?,
        val lineHeight: Int,
        val fontWeight: FontWeight,
        val letterSpacing: Float? = null,
        // val boxShadow: Int? = null,
        val borderRadius: Int,
        val backgroundColor: Color,
        val textTransform: TextTransform? = null,
    ) {
        fun withOverrides(overrides: ActionButtonAtom?): Resolved {
            if (overrides == null) return this
            return Resolved(
                fontSize = overrides.fontSize ?: fontSize,
                fontColor = overrides.fontColor ?: fontColor,
                height = overrides.height ?: height,
                lineHeight = overrides.lineHeight ?: lineHeight,
                fontWeight = overrides.fontWeight ?: fontWeight,
                letterSpacing = overrides.letterSpacing ?: letterSpacing,
                borderRadius = overrides.borderRadius ?: borderRadius,
                backgroundColor = overrides.backgroundColor ?: backgroundColor,
                textTransform = overrides.textTransform ?: textTransform,
            )
        }
    }

    internal companion object {
        fun defaults(theme: ThemeConfig): Resolved {
            val defaultTypescale = theme.typescale.buttonLabel
            return Resolved(
                fontSize = defaultTypescale.fontSize,
                fontColor = theme.regularButtonTextColor,
                height = null,
                lineHeight = defaultTypescale.lineHeight,
                fontWeight = defaultTypescale.fontWeight,
                letterSpacing = defaultTypescale.letterSpacing,
//                boxShadow = ,
                borderRadius = theme.styleTokens.buttonBorderRadius,
                backgroundColor = theme.regularButtonBackgroundColor,
                textTransform = null,
            )
        }
    }
}
