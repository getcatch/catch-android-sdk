package com.getcatch.android.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.getcatch.android.models.RewardCampaign
import com.getcatch.android.utils.fromHexString

@Composable
internal fun RewardCampaignCard(
    rewardCampaign: RewardCampaign,
) {
    RewardCard(
        rewardsAmount = rewardCampaign.totalAmount.toInt(),
        logoUrl = rewardCampaign.cardLogoImageUrl,
        expirationDate = rewardCampaign.expirationDate,
        cardBgColor = Color.fromHexString(rewardCampaign.merchantCardBackgroundColor),
        textColor = Color.fromHexString(rewardCampaign.merchantCardFontColor),
        cardBgImageUrl = rewardCampaign.merchantCardBackgroundImageUrl,
    )
}
