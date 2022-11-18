package com.getcatch.android.theming.atomization.atoms

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.theming.ThemeConfig
import com.getcatch.android.theming.atomization.TextTransform

/**
 * Attributes that can be customized on a specific text target.
 */
public data class TextAtom(
    /**
     * Applies to all elements - benefit text, filler text, and info button.
     */
    val fontSize: Int? = null,

    /**
     * Applies to text elements - benefit text and filler text.
     */
    val fontFamily: FontFamily? = null,

    /**
     * Applies to the filler text and info button. Benefit text is controlled separately.
     */
    val fontColor: Color? = null,

    /**
     *
     */
    val lineHeight: Int? = null,
    val letterSpacing: Int? = null,
    val textTransform: TextTransform? = null,
) {

    internal data class Resolved(
        val fontSize: Int,
        val fontFamily: FontFamily,
        val fontColor: Color,
        val lineHeight: Int,
        val letterSpacing: Int?, // Nullable because can default letter spacing
        val textTransform: TextTransform?, // Nullable because transformation not required
    ) {
        fun withOverrides(overrides: TextAtom?): Resolved {
            if (overrides == null) return this
            return Resolved(
                fontSize = overrides.fontSize ?: fontSize,
                fontFamily = overrides.fontFamily ?: fontFamily,
                fontColor = overrides.fontColor ?: fontColor,
                lineHeight = overrides.lineHeight ?: lineHeight,
                letterSpacing = overrides.letterSpacing ?: letterSpacing,
                textTransform = overrides.textTransform ?: textTransform,
            )
        }
    }

    internal companion object {
        internal fun defaults(theme: ThemeConfig): Resolved {
            val regularTypeScale = theme.typescale.bodyRegular
            return Resolved(
                fontSize = regularTypeScale.fontSize,
                fontFamily = CatchTypography.circularFontFamily,
                fontColor = theme.primaryTextColor,
                lineHeight = regularTypeScale.lineHeight,
                letterSpacing = null,
                textTransform = null,
            )
        }
    }
}
