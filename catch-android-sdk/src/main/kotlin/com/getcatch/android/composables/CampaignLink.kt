package com.getcatch.android.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.R
import com.getcatch.android.composables.elements.LinkButton
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.LocalThemeVariant
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.typography.CatchTextStyles
import com.getcatch.android.utils.centsToDollarsString
import org.koin.compose.koinInject

@Composable
public fun CampaignLink(
    rewardsAmount: Int,
    borderStyle: BorderStyle? = null,
) {
    CampaignLinkInternal(
        rewardsAmount = rewardsAmount,
        borderStyle = borderStyle,
    )
}

@Composable
internal fun CampaignLinkInternal(
    rewardsAmount: Int,
    borderStyle: BorderStyle? = null,
    merchantRepo: MerchantRepository = koinInject(),
) {
    val merchant by merchantRepo.activeMerchant.collectAsStateWithLifecycle()
    CatchTheme {
        var containerModifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .animateContentSize()
        if (borderStyle != null) {
            containerModifier =
                containerModifier
                    .border(1.dp, CatchTheme.colors.border, borderStyle.shape)
                    .padding(16.dp)
        }
        Column(modifier = containerModifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = LocalThemeVariant.current.logoResId),
                contentDescription = stringResource(
                    id = R.string.content_description_catch_logo
                ),
                modifier = Modifier.height(28.dp),
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W700,
                            color = CatchTheme.colors.accent
                        )
                    ) {
                        append(
                            stringResource(
                                id = R.string.you_earned,
                                rewardsAmount.centsToDollarsString()
                            )
                        )
                    }
                    merchant?.let {
                        append(stringResource(id = R.string.to_spend_at_next_time, it.name))
                    }
                },
                style = CatchTextStyles.bodyRegular
            )
            MerchantRewardCard(rewardsAmount = rewardsAmount)
            Text(
                text = stringResource(id = R.string.claim_now_and_start_earning),
                style = CatchTextStyles.bodySmall
            )
            LinkButton(
                label = stringResource(
                    id = R.string.claim_store_credit,
                    rewardsAmount.centsToDollarsString()
                ),
                link = "https://getcatch.com"
            )
        }
    }
}
