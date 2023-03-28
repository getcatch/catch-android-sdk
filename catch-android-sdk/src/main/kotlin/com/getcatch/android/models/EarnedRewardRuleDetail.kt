package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class EarnedRewardRuleDetail(
    val rewardRuleId: String,
    val earnedRewardAmount: Int,
    val userFacingName: String?,
    val sortOrder: Int?,
    val detailsLink: String?,
    val rewardAmountType: String?,
    val ruleEngineType: String?,
    val flatRewardAmount: Int?,
    val percentageRewardRate: Double?,
)
