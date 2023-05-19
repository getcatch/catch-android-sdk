package com.getcatch.android.models.tofu

import com.getcatch.android.models.AvailableRewardDetail
import kotlinx.serialization.Serializable

@Serializable
internal data class TofuAvailableRewardsBreakdown(
    val redeemableRewardsTotal: Int,
    val restrictedRewards: List<AvailableRewardDetail>,
    val restrictedRewardsTotal: Int
)
