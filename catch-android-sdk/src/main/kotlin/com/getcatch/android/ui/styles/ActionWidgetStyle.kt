package com.getcatch.android.ui.styles

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

public data class ActionWidgetStyle(
    val widgetTextStyle: WidgetTextStyle? = null,
    val benefitTextStyle: BenefitTextStyle? = null,
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
