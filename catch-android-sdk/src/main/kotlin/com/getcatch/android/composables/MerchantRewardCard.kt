package com.getcatch.android.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.utils.fromHexString
import org.koin.androidx.compose.get

@Composable
internal fun MerchantRewardCard(rewardsAmount: Int, merchantRepo: MerchantRepository = get()) {
    val merchant by merchantRepo.activeMerchant.collectAsState()
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
