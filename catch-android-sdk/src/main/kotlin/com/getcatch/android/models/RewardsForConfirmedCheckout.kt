package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class RewardsForConfirmedCheckout(
    val earnedRewards: Int,
    val userDonationAmountFromReward: Int?,
)
