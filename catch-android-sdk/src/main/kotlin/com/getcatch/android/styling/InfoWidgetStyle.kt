package com.getcatch.android.styling

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.text.TextStyle as ComposeTextStyle

public data class InfoWidgetStyle(
    val widgetTextStyle: WidgetTextStyle? = null,
    val benefitTextStyle: BenefitTextStyle? = null,
) {

    internal fun withOverrides(overrides: InfoWidgetStyle?): InfoWidgetStyle {
        if (overrides == null) return this
        return InfoWidgetStyle(
            widgetTextStyle = widgetTextStyle?.withOverrides(overrides.widgetTextStyle),
            benefitTextStyle = benefitTextStyle?.withOverrides(overrides.benefitTextStyle),
        )
    }

    internal data class Resolved(
        val widgetTextStyle: WidgetTextStyle.Resolved,
        val benefitTextStyle: BenefitTextStyle.Resolved,
    ) {
        fun withOverrides(overrides: InfoWidgetStyle?): Resolved {
            if (overrides == null) return this
            return Resolved(
                widgetTextStyle = widgetTextStyle.withOverrides(overrides.widgetTextStyle),
                benefitTextStyle = benefitTextStyle.withOverrides(overrides.benefitTextStyle)
            )
        }

        val composeTextStyle = ComposeTextStyle(
            color = widgetTextStyle.fontColor,
            fontSize = widgetTextStyle.fontSize,
            fontWeight = widgetTextStyle.fontWeight,
            lineHeight = widgetTextStyle.lineHeight,
            letterSpacing = widgetTextStyle.letterSpacing ?: TextUnit.Unspecified,
        )

        val benefitComposeTextStyle = composeTextStyle.copy(
            fontWeight = benefitTextStyle.fontWeight
        )

        fun applyTextTransform(text: String) =
            widgetTextStyle.textTransform?.transform(text) ?: text
    }
}
