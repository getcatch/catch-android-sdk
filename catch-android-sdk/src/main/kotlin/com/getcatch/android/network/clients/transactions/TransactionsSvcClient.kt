package com.getcatch.android.network.clients.transactions

import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.Item
import com.getcatch.android.models.PublicKey
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.models.RewardCampaign
import com.getcatch.android.models.RewardsForConfirmedCheckout
import com.getcatch.android.network.NetworkResponse

internal interface TransactionsSvcClient {
    suspend fun fetchUserData(
        deviceToken: String,
        merchantId: String
    ): NetworkResponse<PublicUserData>

    suspend fun calculateEarnedRewards(
        merchantId: String,
        amountEligibleForEarnedRewards: Int,
        isNewCatchUser: Boolean,
        items: List<Item>?,
        userCohorts: List<String>?,
        excludeCartBasedRules: Boolean = false,
        includeEarnedRewardBreakdown: Boolean = true,
    ): NetworkResponse<EarnedRewardsSummary>

    suspend fun fetchRewardCampaign(campaignName: String): NetworkResponse<RewardCampaign>

    suspend fun fetchRewardsForConfirmedCheckout(
        orderId: String,
        publicKey: PublicKey
    ): NetworkResponse<RewardsForConfirmedCheckout>
}
