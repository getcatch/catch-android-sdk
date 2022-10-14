package com.getcatch.android.domain

internal data class PublicUserData(

    /**
     * The user's public first name
     */
    val userFirstName: String,

    /**
     * Amount of rewards (in US cents) the the user has available for use at this merchant.
     * Includes flex rewards.
     */
    val rewardAmount: Int,

    /**
     * Whether or not the current user is eligible for a first time purchase bonus
     */
    val firstPurchaseBonusEligibility: Boolean,
)
