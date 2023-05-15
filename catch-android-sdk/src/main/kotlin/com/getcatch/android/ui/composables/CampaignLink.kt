package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.R
import com.getcatch.android.models.RewardCampaign
import com.getcatch.android.ui.ActionWidgetType
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.composables.elements.CatchText
import com.getcatch.android.ui.composables.elements.LinkButton
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.Constants
import com.getcatch.android.utils.border
import com.getcatch.android.utils.logoResId
import com.getcatch.android.utils.transformAndAppend
import com.getcatch.android.viewmodels.CampaignLinkUiState
import com.getcatch.android.viewmodels.CampaignLinkViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * The CampaignLink widget is designed to be displayed on your order confirmation page if Catch was
 * **not** used as a payment method to offer credits to the consumer if they pay with Catch for
 * their next purchase.
 *
 * The widget displays information about the amount of credits the consumer can claim based on the
 * reward campaign’s name. The widget also acts as a hyperlink, directing consumers to a page where
 * they can claim the credits.
 *
 * @param campaignName The name of a valid and active Catch campaign.
 *
 * @param borderStyle The [BorderStyle] that the widget renders.
 * Defaults to the [BorderStyle.SlightRound] style.
 *
 * @param theme The Catch color [ThemeVariant]. If no theme is set, the theme set globally on the
 * [Catch] object will be used, which defaults to [ThemeVariant.Light].
 *
 * @param styleOverrides Style overrides which can be used to override the widget's default
 * appearance (ex. font size, color, weight, etc.).
 */
@Composable
public fun CampaignLink(
    campaignName: String,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    theme: ThemeVariant? = null,
    styleOverrides: ActionWidgetStyle? = null,
) {
    val viewModel: CampaignLinkViewModel = koinViewModel(key = campaignName)
    LaunchedEffect(campaignName) {
        viewModel.loadCampaign(campaignName)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState is CampaignLinkUiState.Success) {
        CampaignLinkInternal(
            rewardCampaign = (uiState as CampaignLinkUiState.Success).rewardCampaign,
            borderStyle = borderStyle,
            theme = theme,
            styleOverrides = styleOverrides,
        )
    }
}

@Composable
internal fun CampaignLinkInternal(
    rewardCampaign: RewardCampaign,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    theme: ThemeVariant? = null,
    styleOverrides: ActionWidgetStyle? = null,
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
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Image(
                    painter = painterResource(id = themeVariant.logoResId),
                    contentDescription = stringResource(id = R.string.content_description_catch_logo),
                    modifier = Modifier.height(28.dp),
                )
                CatchText(
                    text = buildAnnotatedString {
                        withStyle(styles.earnedComposeSpanStyle) {
                            transformAndAppend(
                                styles.widgetTextStyle.textTransform,
                                stringResource(
                                    id = R.string.you_earned,
                                    rewardCampaign.amountInDollars
                                )
                            )
                        }
                        transformAndAppend(
                            styles.widgetTextStyle.textTransform,
                            stringResource(
                                id = R.string.to_spend_at_next_time,
                                rewardCampaign.merchantName
                            )
                        )
                    },
                    style = styles.composeTextStyle
                )
                RewardCampaignCard(rewardCampaign = rewardCampaign)
                CatchText(
                    text = stringResource(id = R.string.claim_now_and_start_earning),
                    style = claimNowTextStyle(styles.composeTextStyle)
                )
                val buttonModifier =
                    if (fullWidthButton) Modifier.fillMaxWidth()
                    else Modifier.wrapContentWidth()
                LinkButton(
                    label = stringResource(
                        id = R.string.claim_store_credit,
                        rewardCampaign.amountInDollars
                    ),
                    link = "https://getcatch.com",
                    modifier = buttonModifier,
                    styles = styles.actionButtonStyle
                )
            }
        }
    }
}

internal fun claimNowTextStyle(baseTextStyle: TextStyle): TextStyle {
    val fontSize = baseTextStyle.fontSize * Constants.CLAIM_NOW_FONT_SIZE_RATIO
    val lineHeight = fontSize * Constants.CLAIM_NOW_FONT_SIZE_TO_LINE_HEIGHT_RATIO
    return baseTextStyle.copy(
        fontSize = fontSize,
        lineHeight = lineHeight,
    )
}


