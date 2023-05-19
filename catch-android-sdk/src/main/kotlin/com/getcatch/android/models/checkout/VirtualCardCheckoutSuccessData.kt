package com.getcatch.android.models.checkout

import kotlinx.serialization.Serializable

@Serializable
internal data class VirtualCardCheckoutSuccessData(
    val totalAfterRewards: Int,
    val appliedRewardsAmount: Int,
    val earnedRewardsAmount: Int,
    val userDonationAmount: Int,
    val signUpDiscountAmount: Int,
    val cardDetails: CardDetails,
)
