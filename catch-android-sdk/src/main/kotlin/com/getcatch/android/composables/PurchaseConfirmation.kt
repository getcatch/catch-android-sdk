package com.getcatch.android.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.composables.elements.LinkButton
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.theming.BorderStyle
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.theming.LocalThemeVariant
import com.getcatch.android.utils.centsToDollarsString
import org.koin.compose.koinInject

@Composable
public fun PurchaseConfirmation(rewardsAmount: Int, borderStyle: BorderStyle? = null) {
    PurchaseConfirmationInternal(
        rewardsAmount = rewardsAmount,
        borderStyle = borderStyle
    )
}

@Composable
internal fun PurchaseConfirmationInternal(
    rewardsAmount: Int,
    borderStyle: BorderStyle? = null,
    merchantRepo: MerchantRepository = koinInject()
) {
    val merchant by merchantRepo.activeMerchant.collectAsState()
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
        Column(modifier = containerModifier) {
            Image(
                painter = painterResource(id = LocalThemeVariant.current.logoResId),
                contentDescription = stringResource(
                    id = R.string.content_description_catch_logo
                ),
                modifier = Modifier.height(28.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                        append(stringResource(id = R.string.to_spend_at, it.name))
                    }
                },
                style = CatchTypography.CatchTextStyles.bodyRegular
            )
            Spacer(modifier = Modifier.height(16.dp))
            MerchantRewardCard(rewardsAmount = rewardsAmount)
            Spacer(modifier = Modifier.height(24.dp))
            LinkButton(
                label = stringResource(id = R.string.view_your_credit),
                link = "https://getcatch.com"
            )
        }
    }
}
