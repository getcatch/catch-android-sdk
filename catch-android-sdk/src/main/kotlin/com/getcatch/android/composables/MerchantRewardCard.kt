package com.getcatch.android.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.getcatch.android.repository.MerchantRepository

@Composable
internal fun MerchantRewardCard(rewardsAmount: Int) {
    val merchant by MerchantRepository.activeMerchant.collectAsState()
    merchant?.let {
        RewardCard(
            rewardsAmount = rewardsAmount,
            logoUrl = it.cardLogoImageUrl,
            cardBgColor = Color.Black,
            textColor = Color.White
        )
    }
}
