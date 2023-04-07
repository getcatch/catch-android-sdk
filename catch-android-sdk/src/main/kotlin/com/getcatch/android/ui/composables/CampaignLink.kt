package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.models.Merchant
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.ui.ActionWidgetType
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.composables.elements.LinkButton
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariantOption
import com.getcatch.android.utils.Constants
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.transformAndAppend
import org.koin.compose.koinInject

@Composable
public fun CampaignLink(
    rewardsAmount: Int,
    borderStyle: BorderStyle? = null,
    theme: ThemeVariantOption? = null,
    styleOverrides: ActionWidgetStyle? = null,
) {
    val merchantRepo: MerchantRepository = koinInject()
    val merchant by merchantRepo.activeMerchant.collectAsState()
    CampaignLinkInternal(
        rewardsAmount = rewardsAmount,
        borderStyle = borderStyle,
        theme = theme,
        styleOverrides = styleOverrides,
        merchant = merchant,
    )
}

@Composable
internal fun CampaignLinkInternal(
    rewardsAmount: Int,
    borderStyle: BorderStyle? = null,
    theme: ThemeVariantOption? = null,
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

        if (borderStyle != null) {
            val borderColor =
                if (borderStyle is BorderStyle.Custom) borderStyle.color
                else CatchTheme.colors.border

            containerModifier = containerModifier
                .border(1.dp, borderColor, borderStyle.shape)
                .padding(16.dp)
        }
        BoxWithConstraints(modifier = containerModifier) {
            val fullWidthButton = maxWidth < 479.dp
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Image(
                    painter = painterResource(id = themeVariant.logoResId),
                    contentDescription = stringResource(id = R.string.content_description_catch_logo),
                    modifier = Modifier.height(28.dp),
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(styles.earnedComposeSpanStyle) {
                            transformAndAppend(
                                styles.widgetTextStyle.textTransform,
                                stringResource(
                                    id = R.string.you_earned,
                                    rewardsAmount.centsToDollarsString()
                                )
                            )
                        }
                        merchant?.let {
                            transformAndAppend(
                                styles.widgetTextStyle.textTransform,
                                stringResource(id = R.string.to_spend_at_next_time, it.name)
                            )
                        }
                    },
                    style = styles.composeTextStyle
                )
                MerchantRewardCard(rewardsAmount = rewardsAmount, merchant = merchant)
                Text(
                    text = stringResource(id = R.string.claim_now_and_start_earning),
                    style = claimNowTextStyle(styles.composeTextStyle)
                )
                val buttonModifier =
                    if (fullWidthButton) Modifier.fillMaxWidth()
                    else Modifier.wrapContentWidth()
                LinkButton(
                    label = stringResource(
                        id = R.string.claim_store_credit,
                        rewardsAmount.centsToDollarsString()
                    ),
                    link = "https://getcatch.com",
                    modifier = buttonModifier,
                    styles = styles.actionButtonStyle
                )
            }
        }
    }
}

internal fun claimNowTextStyle(baseTextStyle: TextStyle): TextStyle  {
    val fontSize = baseTextStyle.fontSize * Constants.CLAIM_NOW_FONT_SIZE_RATIO
    val lineHeight = fontSize * Constants.CLAIM_NOW_FONT_SIZE_TO_LINE_HEIGHT_RATIO
    return baseTextStyle.copy(
        fontSize = fontSize,
        lineHeight = lineHeight,
    )
}


