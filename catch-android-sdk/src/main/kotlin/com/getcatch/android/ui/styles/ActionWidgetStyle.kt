package com.getcatch.android.ui.styles

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

/**
 * Styling for Catch widgets which contain an action button.
 *
 * This includes the following widgets:
 *  - [PurchaseConfirmation]
 *  - [CampaignLink]
 */
public data class ActionWidgetStyle(
    /** Configures styling for all text components in an action widget. */
    val widgetTextStyle: WidgetTextStyle? = null,

    /** Configures color and font weight of all benefit text within an action widget. */
    val benefitTextStyle: BenefitTextStyle? = null,

    /** Configures styling of the button within an action widget. */
    val actionButtonStyle: ActionButtonStyle? = null,
) {
    internal fun withOverrides(overrides: ActionWidgetStyle?): ActionWidgetStyle {
        if (overrides == null) return this
        return ActionWidgetStyle(
            widgetTextStyle = widgetTextStyle?.withOverrides(overrides.widgetTextStyle),
            benefitTextStyle = benefitTextStyle?.withOverrides(overrides.benefitTextStyle),
            actionButtonStyle = actionButtonStyle?.withOverrides(overrides.actionButtonStyle),
        )
    }

    internal data class Resolved(
        val widgetTextStyle: WidgetTextStyle.Resolved,
        val benefitTextStyle: BenefitTextStyle.Resolved,
        val actionButtonStyle: ActionButtonStyle.Resolved,
    ) {
        fun withOverrides(overrides: ActionWidgetStyle?): Resolved {
            if (overrides == null) return this
            return Resolved(
                widgetTextStyle = widgetTextStyle.withOverrides(overrides.widgetTextStyle),
                benefitTextStyle = benefitTextStyle.withOverrides(overrides.benefitTextStyle),
                actionButtonStyle = actionButtonStyle.withOverrides(overrides.actionButtonStyle),
            )
        }

        val composeTextStyle = TextStyle(
            color = widgetTextStyle.fontColor,
            fontSize = widgetTextStyle.fontSize,
            fontWeight = widgetTextStyle.fontWeight,
            lineHeight = widgetTextStyle.lineHeight,
            letterSpacing = widgetTextStyle.letterSpacing ?: TextUnit.Unspecified,
        )

        val earnedComposeSpanStyle = SpanStyle(
            color = benefitTextStyle.earnFontColor,
            fontWeight = benefitTextStyle.fontWeight,
        )

        fun applyTextTransform(text: String) =
            widgetTextStyle.textTransform?.transform(text) ?: text

    }
}
