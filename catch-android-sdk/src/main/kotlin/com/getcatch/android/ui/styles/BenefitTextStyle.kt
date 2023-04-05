package com.getcatch.android.ui.styles

import androidx.compose.ui.graphics.Color
import com.getcatch.android.ui.styles.values.ColorValue
import com.getcatch.android.ui.styles.values.FontWeight
import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight

public data class BenefitTextStyle(
    val fontWeight: FontWeight? = null,
    val earnFontColor: ColorValue? = null,
    val redeemFontColor: ColorValue? = null,
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
                fontWeight = overrides.fontWeight?.toComposeFontWeight() ?: fontWeight,
                earnFontColor = overrides.earnFontColor?.value ?: earnFontColor,
                redeemFontColor = overrides.redeemFontColor?.value ?: redeemFontColor,
            )
        }
    }
}