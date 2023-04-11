package com.getcatch.android.ui.styles

import androidx.compose.ui.text.font.FontWeight
import com.getcatch.android.Catch
import com.getcatch.android.ui.InfoWidgetType
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.ui.styles.values.FontWeight as CatchFontWeight

internal object StyleResolver {

    fun infoWidgetStyles(
        theme: ThemeVariant,
        instanceOverrides: InfoWidgetStyle?,
        infoWidgetType: InfoWidgetType,
    ): InfoWidgetStyle.Resolved {
        val defaults = InfoWidgetStyle.Resolved(
            widgetTextStyle = WidgetTextStyle.Resolved(
                fontSize = infoWidgetType.defaultTextStyle.fontSize,
                lineHeight = infoWidgetType.defaultTextStyle.lineHeight,
                fontColor = theme.composeColors.foreground,
                fontWeight = FontWeight.W400,
            ),
            benefitTextStyle = BenefitTextStyle.Resolved(
                fontWeight = FontWeight.W700,
                earnFontColor = theme.composeColors.accent,
                redeemFontColor = theme.composeColors.secondaryAccent,
            )
        )
        val globalOverrides = Catch.styleConfig.let { globalConfig ->
            val basicOverrides = InfoWidgetStyle(
                widgetTextStyle = globalConfig?.widgetTextStyle,
                benefitTextStyle = globalConfig?.benefitTextStyle,
            )
            val widgetOverrides = when (infoWidgetType) {
                InfoWidgetType.Callout -> globalConfig?.calloutStyle
                is InfoWidgetType.PaymentMethod -> globalConfig?.paymentMethodStyle
                InfoWidgetType.ExpressCheckoutCallout -> globalConfig?.expressCheckoutCalloutStyle
            }
            return@let basicOverrides.withOverrides(widgetOverrides)
        }
        val fullyResolvedStyles = defaults
            .withOverrides(globalOverrides)
            .withOverrides(instanceOverrides)

        // The ExpressCheckoutCallout does not allow for font weight overrides
        if (infoWidgetType is InfoWidgetType.ExpressCheckoutCallout) {
            return fullyResolvedStyles.withOverrides(
                InfoWidgetStyle(
                    widgetTextStyle = WidgetTextStyle(fontWeight = CatchFontWeight.W400),
                    benefitTextStyle = BenefitTextStyle(fontWeight = CatchFontWeight.W700)
                )
            )
        }

        return fullyResolvedStyles
    }
}
