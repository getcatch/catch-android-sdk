package com.getcatch.android.ui.styles

import androidx.compose.ui.graphics.Color
import com.getcatch.android.ui.styles.values.ColorValue
import com.getcatch.android.ui.styles.values.FontWeight
import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

/**
 * Styling for the benefit text within Catch widgets.
 * The benefit text displays the earned or redeemable credits to the user.
 *
 * Examples: "You earned $x", "Redeem $x" or "Earn x% credit".
 *
 * This portion of the text can be highlighted with a different text color and/or font weight.
 * All other text attributes will be inherited from the general [WidgetTextStyle].
 */
public data class BenefitTextStyle(
    /** Configures the font weight of the benefit text. */
    val fontWeight: ComposeFontWeight? = null,

    /**
     * Configures the text color of the benefit text in the case when a user is earning credits.
     * Example: "Earn $x credit"
     */
    val earnFontColor: Color? = null,

    /**
     * Configures the text color of the benefit text in the case when a user is redeeming credits.
     * Example: "Redeem $x"
     */
    val redeemFontColor: Color? = null,
) {

    internal fun withOverrides(overrides: BenefitTextStyle?): BenefitTextStyle {
        if (overrides == null) return this
        return BenefitTextStyle(
            fontWeight = overrides.fontWeight ?: fontWeight,
            earnFontColor = overrides.earnFontColor ?: earnFontColor,
            redeemFontColor = overrides.redeemFontColor ?: redeemFontColor,
        )
    }

    internal data class Resolved(
        val fontWeight: ComposeFontWeight,
        val earnFontColor: Color,
        val redeemFontColor: Color,
    ) {
        fun withOverrides(overrides: BenefitTextStyle?): Resolved {
            if (overrides == null) return this
            return Resolved(
                fontWeight = overrides.fontWeight ?: fontWeight,
                earnFontColor = overrides.earnFontColor ?: earnFontColor,
                redeemFontColor = overrides.redeemFontColor ?: redeemFontColor,
            )
        }
    }

    public companion object {
        /**
         * Utility function to replace primary constructor for view based apps that use [ColorValue]
         * and [FontWeight] instead of the Compose [androidx.compose.ui.graphics.Color] and
         * [androidx.compose.ui.text.font.FontWeight].
         */
        public fun create(
            fontWeight: FontWeight? = null,
            earnFontColor: ColorValue? = null,
            redeemFontColor: ColorValue? = null,
        ): BenefitTextStyle = BenefitTextStyle(
            fontWeight = fontWeight?.toComposeFontWeight(),
            earnFontColor = earnFontColor?.value,
            redeemFontColor = redeemFontColor?.value,
        )
    }
}
