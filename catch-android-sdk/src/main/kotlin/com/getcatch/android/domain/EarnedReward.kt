package com.getcatch.android.domain

internal data class EarnedReward(
    /**
     * Amount of rewards (in US cents) that the user can earn as a sign up bonus.
     */
    val signUpDiscountAmount: Int,

    /**
     * Between 0 and 1, the fraction of amount paid that a user earns back from their purchase.
     */
    val percentageRewardRate: Float
)
