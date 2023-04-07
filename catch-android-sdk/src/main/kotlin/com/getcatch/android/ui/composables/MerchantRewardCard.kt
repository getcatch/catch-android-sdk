package com.getcatch.android.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.models.Merchant
import com.getcatch.android.utils.PreviewData
import com.getcatch.android.utils.fromHexString

@Composable
internal fun MerchantRewardCard(
    rewardsAmount: Int,
    merchant: Merchant?
) {
    merchant?.let {
        RewardCard(
            rewardsAmount = rewardsAmount,
            logoUrl = it.cardLogoImageUrl,
            cardBgColor = Color.fromHexString(it.cardBackgroundColor, Color.Black),
            textColor = Color.fromHexString(it.cardFontColor, Color.White),
            cardBgImageUrl = it.cardBackgroundImageUrl,
        )
    }
}

@Preview
@Composable
internal fun PreviewMerchantRewardCard() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        MerchantRewardCard(rewardsAmount = 1000, merchant = PreviewData.merchant)
    }
}
