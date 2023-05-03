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
import com.getcatch.android.ui.HasBorderShape
import com.getcatch.android.ui.composables.elements.CatchText
import com.getcatch.android.ui.composables.elements.LinkButton
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariantOption
import com.getcatch.android.utils.Constants
import com.getcatch.android.utils.transformAndAppend
import com.getcatch.android.viewmodels.CampaignLinkUiState
import com.getcatch.android.viewmodels.CampaignLinkViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun CampaignLink(
    campaignName: String,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    theme: ThemeVariantOption? = null,
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
    theme: ThemeVariantOption? = null,
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


