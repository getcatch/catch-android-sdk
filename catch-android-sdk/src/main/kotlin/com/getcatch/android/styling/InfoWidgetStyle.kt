package com.getcatch.android.styling

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.text.TextStyle as ComposeTextStyle

public data class InfoWidgetStyle(
    val textStyle: TextStyle? = null,
    val benefitTextStyle: BenefitTextStyle? = null,
) {

    internal fun withOverrides(overrides: InfoWidgetStyle?): InfoWidgetStyle {
        if (overrides == null) return this
        return InfoWidgetStyle(
            textStyle = textStyle?.withOverrides(overrides.textStyle),
            benefitTextStyle = benefitTextStyle?.withOverrides(overrides.benefitTextStyle),
        )
    }
    internal data class Resolved(
        val textStyle: TextStyle.Resolved,
        val benefitTextStyle: BenefitTextStyle.Resolved,
    ) {
        fun withOverrides(overrides: InfoWidgetStyle?): Resolved {
            if (overrides == null) return this
            return Resolved(
                textStyle = textStyle.withOverrides(overrides.textStyle),
                benefitTextStyle = benefitTextStyle.withOverrides(overrides.benefitTextStyle)
            )
        }

        val composeTextStyle: ComposeTextStyle
            get() = ComposeTextStyle(
                color = textStyle.fontColor,
                fontSize = textStyle.fontSize,
                fontWeight = textStyle.fontWeight,
                lineHeight = textStyle.lineHeight,
                letterSpacing = textStyle.letterSpacing ?: TextUnit.Unspecified,
            )
    }
}
