package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class PublicUserData(

    /** The user's public first name */
    val userFirstName: String,

    /**
     * Amount of rewards (in US cents) the the user has available for use at this merchant.
     * Includes flex rewards.
     */
    val rewardAmount: Int?,

    /**
     * Whether or not the current user is eligible for a first time purchase bonus
     */
    val firstPurchaseBonusEligibility: Boolean,

    /** Whether or not the current user is an employee of Catch */
    val isCatchEmployee: Boolean = false,

    /** Whether or not the current user was referred to the merchant by Catch */
    val wasReferred: Boolean = false,

    /** A list of rewards available for a user to redeem. */
    val availableRewardBreakdown: List<AvailableRewardDetail>
) {
    companion object {
        val noData = PublicUserData(
            userFirstName = "",
            rewardAmount = 0,
            firstPurchaseBonusEligibility = true,
            isCatchEmployee = false,
            wasReferred = false,
            availableRewardBreakdown = emptyList(),
        )
    }
}
