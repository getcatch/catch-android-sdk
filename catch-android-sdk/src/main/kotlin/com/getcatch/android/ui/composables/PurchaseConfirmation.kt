package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.Catch
import com.getcatch.android.R
import com.getcatch.android.models.Merchant
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.ui.ActionWidgetType
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.composables.elements.CatchText
import com.getcatch.android.ui.composables.elements.DonationMessage
import com.getcatch.android.ui.composables.elements.LinkButton
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.utils.PreviewData
import com.getcatch.android.utils.border
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.logoResId
import com.getcatch.android.utils.transformAndAppend
import org.koin.compose.koinInject

/**
 * The `PurchaseConfirmation` widget is designed to be used on the merchant's order
 * confirmation page if Catch was used as a payment method.
 *
 * The widget includes information about how much credit the consumer just earned
 * through their purchase and contains a link which directs the consumer to their account
 * page on Catch's website.
 *
 * For virtual card integrations, use
 * [`PurchaseConfirmationByOrderId`](PurchaseConfirmationByOrderId).
 *
 * @param earned The amount in cents that that the consumer earned in credit based on their purchase.
 *
 * @param donation The amount of cents that the consumer is donating. Not used if the merchant
 * doesn't have donations enabled.
 *
 * @param borderStyle The [`BorderStyle`](BorderStyle) that the widget renders.
 * Defaults to the [`BorderStyle.SlightRound`](BorderStyle.SlightRound) style.
 *
 * @param colorTheme The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set
 * globally on the [`Catch`](Catch) object will be used, which defaults to
 * [`CatchColorTheme.Light`](CatchColorTheme.Light).
 *
 * @param styleOverrides Style overrides which can be used to override the widget's default
 * appearance (ex. font size, color, weight, etc.).
 */
@Composable
public fun PurchaseConfirmation(
    earned: Int,
    donation: Int? = null,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    colorTheme: CatchColorTheme? = null,
    styleOverrides: ActionWidgetStyle? = null,
) {
    Catch.assertInitialized()
    val merchantRepo: MerchantRepository = koinInject()
    val merchant by merchantRepo.activeMerchant.collectAsState()
    PurchaseConfirmationInternal(
        earned = earned,
        donation = donation ?: 0,
        borderStyle = borderStyle,
        colorTheme = colorTheme,
        styleOverrides = styleOverrides,
        merchant = merchant,
    )
}

@Composable
internal fun PurchaseConfirmationInternal(
    earned: Int,
    donation: Int,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    colorTheme: CatchColorTheme? = null,
    styleOverrides: ActionWidgetStyle? = null,
    merchant: Merchant?,
) {
    CatchTheme(colorTheme) {
        val themeColors = CatchTheme.colors
        val styles by remember(CatchTheme.colorTheme, styleOverrides) {
            mutableStateOf(
                StyleResolver.actionWidgetStyles(
                    themeColors = themeColors,
                    instanceOverrides = styleOverrides,
                    actionWidgetType = ActionWidgetType.PurchaseConfirmation
                )
            )
        }
        var containerModifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .animateContentSize()

        containerModifier = if (borderStyle !is BorderStyle.None) {
            containerModifier
                .border(borderStyle)
                .padding(16.dp)
        } else {
            // If we don't have a border, we need at least a small amount of
            // padding so the action button's shadow does not get cut off
            containerModifier.padding(styles.actionButtonStyle.elevation * 2)
        }

        BoxWithConstraints(modifier = containerModifier) {
            val fullWidthButton = maxWidth < 479.dp
            Column {
                Image(
                    painter = painterResource(id = CatchTheme.colorTheme.logoResId),
                    contentDescription = stringResource(id = R.string.content_description_catch_logo),
                    modifier = Modifier.height(28.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                CatchText(
                    text = buildAnnotatedString {
                        withStyle(styles.earnedComposeSpanStyle) {
                            transformAndAppend(
                                styles.widgetTextStyle.textTransform,
                                stringResource(
                                    id = R.string.you_earned, earned.centsToDollarsString()
                                )
                            )
                        }
                        merchant?.let {
                            transformAndAppend(
                                styles.widgetTextStyle.textTransform,
                                stringResource(id = R.string.to_spend_at, it.name)
                            )
                        }
                    },
                    style = styles.composeTextStyle
                )
                Spacer(modifier = Modifier.height(16.dp))
                MerchantRewardCard(rewardsAmount = earned, merchant = merchant)
                Spacer(modifier = Modifier.height(24.dp))
                val buttonModifier =
                    if (fullWidthButton) Modifier.fillMaxWidth()
                    else Modifier.wrapContentWidth()
                LinkButton(
                    modifier = buttonModifier,
                    label = stringResource(id = R.string.view_your_credit),
                    link = "https://getcatch.com",
                    styles = styles.actionButtonStyle
                )
                if (donation > 0 && merchant != null && merchant.donationRecipient != null) {
                    Spacer(modifier = Modifier.height(24.dp))
                    DonationMessage(
                        donation = donation,
                        merchantName = merchant.name,
                        recipient = merchant.donationRecipient,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewPurchaseConfirmationInternal() {
    PurchaseConfirmationInternal(earned = 1000, donation = 50, merchant = PreviewData.merchant)
}
