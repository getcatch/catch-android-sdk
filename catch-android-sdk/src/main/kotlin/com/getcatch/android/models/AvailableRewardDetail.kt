package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class AvailableRewardDetail(
    val rewardIds: List<String>,
    val amount: Int,
    val rewardAmounts: List<Int>,
    val expirations: List<String>,
    val redeemableFlatOrderTotalMin: Int?,
    val redeemablePercentageOrderTotalMax: Float?,
)
