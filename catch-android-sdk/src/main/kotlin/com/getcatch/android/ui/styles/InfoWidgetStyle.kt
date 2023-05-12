package com.getcatch.android.ui.styles

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.text.TextStyle as ComposeTextStyle

/**
 * Styling for Catch widgets which contain benefit text, filler text, and an info button.
 *
 * This includes the following widgets:
 *  - [Callout]
 *  - [ExpressCheckoutCallout]
 *  - [PaymentMethod]
 */
public data class InfoWidgetStyle(
    /** Configures styling for all text components in an info widget. */
    val widgetTextStyle: WidgetTextStyle? = null,

    /** Configures color and font weight of all benefit text within an info widget. */
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
