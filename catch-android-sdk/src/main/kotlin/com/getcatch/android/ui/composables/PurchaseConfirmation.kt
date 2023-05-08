package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import com.getcatch.android.R
import com.getcatch.android.models.Merchant
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.ui.ActionWidgetType
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.HasBorderShape
import com.getcatch.android.ui.composables.elements.CatchText
import com.getcatch.android.ui.composables.elements.DonationMessage
import com.getcatch.android.ui.composables.elements.LinkButton
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.PreviewData
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.transformAndAppend
import org.koin.compose.koinInject

@Composable
public fun PurchaseConfirmation(
    earned: Int,
    donation: Int? = null,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    theme: ThemeVariant? = null,
    styleOverrides: ActionWidgetStyle? = null,
) {
    val merchantRepo: MerchantRepository = koinInject()
    val merchant by merchantRepo.activeMerchant.collectAsState()
    PurchaseConfirmationInternal(
        earned = earned,
        donation = donation ?: 0,
        borderStyle = borderStyle,
        theme = theme,
        styleOverrides = styleOverrides,
        merchant = merchant,
    )
}

@Composable
internal fun PurchaseConfirmationInternal(
    earned: Int,
    donation: Int,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    theme: ThemeVariant? = null,
    styleOverrides: ActionWidgetStyle? = null,
    merchant: Merchant?,
) {
    CatchTheme(theme) {
        val themeVariant = CatchTheme.variant
        val styles by remember(themeVariant, styleOverrides) {
            mutableStateOf(
                StyleResolver.actionWidgetStyles(
                    theme = themeVariant,
                    instanceOverrides = styleOverrides,
                    actionWidgetType = ActionWidgetType.PurchaseConfirmation
                )
            )
        }
        var containerModifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .animateContentSize()
        containerModifier = if (borderStyle is HasBorderShape) {
            val borderColor =
                if (borderStyle is BorderStyle.Custom) borderStyle.color
                else CatchTheme.colors.border

            containerModifier
                .border(1.dp, borderColor, borderStyle.shape)
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
                    painter = painterResource(id = themeVariant.logoResId),
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
