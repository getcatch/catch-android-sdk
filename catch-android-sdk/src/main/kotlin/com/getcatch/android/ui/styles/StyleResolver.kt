package com.getcatch.android.ui.styles

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.getcatch.android.Catch
import com.getcatch.android.ui.ActionWidgetType
import com.getcatch.android.ui.InfoWidgetType
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.ui.typography.CatchTextStyles
import com.getcatch.android.utils.Constants

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
                    widgetTextStyle = WidgetTextStyle(fontWeight = FontWeight.W400),
                    benefitTextStyle = BenefitTextStyle(fontWeight = FontWeight.W700)
                )
            )
        }

        return fullyResolvedStyles
    }

    fun actionWidgetStyles(
        theme: ThemeVariant,
        instanceOverrides: ActionWidgetStyle?,
        actionWidgetType: ActionWidgetType,
    ): ActionWidgetStyle.Resolved {
        val defaults = ActionWidgetStyle.Resolved(
            widgetTextStyle = WidgetTextStyle.Resolved(
                fontSize = CatchTextStyles.bodyRegular.fontSize,
                lineHeight = CatchTextStyles.bodyRegular.lineHeight,
                fontColor = theme.composeColors.foreground,
                fontWeight = FontWeight.W400,
            ),
            benefitTextStyle = BenefitTextStyle.Resolved(
                fontWeight = FontWeight.W700,
                earnFontColor = theme.composeColors.accent,
                redeemFontColor = theme.composeColors.secondaryAccent,
            ),
            actionButtonStyle = ActionButtonStyle.Resolved(
                fontSize = CatchTextStyles.buttonLabel.fontSize,
                fontColor = theme.composeColors.buttonText,
                height = Constants.DEFAULT_BUTTON_HEIGHT.dp,
                lineHeight = CatchTextStyles.buttonLabel.lineHeight,
                fontWeight = FontWeight.W500,
                letterSpacing = null,
                elevation = Constants.DEFAULT_BUTTON_ELEVATION.dp,
                borderRadius = Constants.DEFAULT_BUTTON_BORDER_RADIUS.dp,
                backgroundColor = theme.composeColors.accent,
                textTransform = null,
            )
        )
        val globalOverrides = Catch.styleConfig.let { globalConfig ->
            val basicOverrides = ActionWidgetStyle(
                widgetTextStyle = globalConfig?.widgetTextStyle,
                benefitTextStyle = globalConfig?.benefitTextStyle,
                actionButtonStyle = globalConfig?.actionButtonStyle,
            )
            val widgetOverrides = when (actionWidgetType) {
                ActionWidgetType.PurchaseConfirmation -> globalConfig?.purchaseConfirmationStyle
                ActionWidgetType.CampaignLink -> globalConfig?.campaignLinkStyle
            }
            return@let basicOverrides.withOverrides(widgetOverrides)
        }
        return defaults
            .withOverrides(globalOverrides)
            .withOverrides(instanceOverrides)
    }
}
