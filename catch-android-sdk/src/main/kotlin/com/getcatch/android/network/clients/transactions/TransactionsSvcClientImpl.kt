package com.getcatch.android.network.clients.transactions

import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.Item
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.models.RewardCampaign
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.utils.handleNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class TransactionsSvcClientImpl(
    private val httpClient: HttpClient,
    environment: Environment
) : TransactionsSvcClient {
    val baseUrl = "${environment.baseUrl}/api/transactions-svc"
    override suspend fun fetchUserData(
        deviceToken: String,
        merchantId: String
    ): NetworkResponse<PublicUserData> = handleNetworkResponse {
        httpClient.get(
            "$baseUrl/user_devices/$deviceToken/user_data"

        ) {
            parameter("merchant_id", merchantId)
        }
    }

    override suspend fun calculateEarnedRewards(
        merchantId: String,
        amountEligibleForEarnedRewards: Int,
        isNewCatchUser: Boolean,
        items: List<Item>?,
        userCohorts: List<String>?,
        excludeCartBasedRules: Boolean,
        includeEarnedRewardBreakdown: Boolean,
    ): NetworkResponse<EarnedRewardsSummary> = handleNetworkResponse {
        httpClient.get("$baseUrl/merchants/$merchantId/calculate_earned_rewards/public") {
            parameter("amount_eligible_for_earned_rewards", amountEligibleForEarnedRewards)
            parameter("is_new_catch_user", isNewCatchUser)
            parameter("exclude_cart_based_rules", excludeCartBasedRules)
            parameter("include_earned_reward_breakdown", includeEarnedRewardBreakdown)
            items?.forEach { parameter("items", it.toQueryString()) }
            userCohorts?.forEach { parameter("user_cohorts", it) }
        }
    }

    override suspend fun fetchRewardCampaign(
        campaignName: String
    ): NetworkResponse<RewardCampaign> = handleNetworkResponse {
        httpClient.get(
            "$baseUrl/reward_campaigns/$campaignName/public"
        )
    }
}
