package com.getcatch.android.theming.atomization.atoms

import androidx.compose.ui.graphics.Color
import com.getcatch.android.theming.ThemeConfig
import com.getcatch.android.theming.atomization.FontWeight

/**
 * The benefit text highlights the primary consumer benefit within the context of a widget.
 *
 * This can vary based on the user (defined at the widget level).
 *
 * In some cases, the benefit text is a link that opens up the earnings breakdown modal.
 */
public data class BenefitTextAtom(
    /**
     * Styles the benefit text in the case of the consumer earning credits.
     * Example: “Earn x% credit.”
     */
    val earnColor: Color? = null,

    /**
     * Styles the benefit text in the case of the consumer redeeming credits.
     * Example: “Redeem $x.”
     */
    val redeemColor: Color? = null,

    /**
     * Sets the font weight of the benefit text.
     */
    val fontWeight: FontWeight? = null,
) {
    internal data class Resolved(
        val earnColor: Color,
        val redeemColor: Color,
        val fontWeight: FontWeight,
    ) {
        fun withOverrides(overrides: BenefitTextAtom?) : Resolved {
            if (overrides == null) return this
            return Resolved(
                earnColor = overrides.earnColor ?: earnColor,
                redeemColor = overrides.redeemColor ?: redeemColor,
                fontWeight = overrides.fontWeight ?: fontWeight
            )
        }
    }

    internal companion object {
        fun defaults(theme: ThemeConfig) = Resolved(
            earnColor = theme.cartEarningCreditsTextColor,
            redeemColor = theme.cartRedeemingCreditsTextColor,
            fontWeight = theme.typescale.heading3.fontWeight,
        )
    }
}
