package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class EarnedRewardsSummary(
    /** Amount of rewards (in US cents) that the user can earn as a sign up bonus. */
    val signUpBonusAmount: Int,

    /** Amount of rewards (in US cents) that the user can earn as a sign up discount. */
    val signUpDiscountAmount: Int,

    /** Between 0 and 1, the fraction of amount paid that a user earns back from their purchase. */
    val percentageRewardRate: Double,

    /** Amount of rewards (in US cents) that the user can earn. */
    val earnedRewardsTotal: Int?,

    /** Rewards rules used for the earnings breakdown modal */
    val earnedRewardBreakdown: List<EarnedRewardRuleDetail>? = null,
)
