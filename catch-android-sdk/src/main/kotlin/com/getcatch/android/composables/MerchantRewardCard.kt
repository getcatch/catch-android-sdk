package com.getcatch.android.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.utils.fromHexString
import org.koin.compose.koinInject

@Composable
internal fun MerchantRewardCard(
    rewardsAmount: Int,
    merchantRepo: MerchantRepository = koinInject()
) {
    val merchant by merchantRepo.activeMerchant.collectAsStateWithLifecycle()
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
