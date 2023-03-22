package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class RewardCampaign(
    /** The unique identifier used to navigate to the reward campaign URL. */
    val rewardCampaignId: String,

    /** Amount of the total reward in cents, must be greater than 0. */
    val totalAmount: Int,

    /** The date when the rewards expire. */
    val rewardsExpiration: String,
)
