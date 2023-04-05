package com.getcatch.android.repository

import android.util.Log
import com.getcatch.android.models.CalculatedReward
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.Item
import com.getcatch.android.models.Merchant
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import kotlin.math.max
import kotlin.math.min

internal class RewardsRepositoryImpl(
    private val merchantRepo: MerchantRepository,
    private val transactionsSvcClient: TransactionsSvcClient,
) : RewardsRepository {
    override suspend fun fetchCalculatedEarnedReward(
        user: PublicUserData,
        price: Int,
        items: List<Item>?,
        userCohorts: List<String>?
    ): CalculatedReward {
        val merchant = merchantRepo.activeMerchant.value ?: return CalculatedReward.Default

        val earnedRewardsSummary = fetchEarnedRewardSummary(
            price = price,
            items = items,
            userCohorts = userCohorts,
            merchant = merchant,
            user = user,
        ) ?: return CalculatedReward.Default

        return getPrioritizedReward(
            earnedRewardsSummary = earnedRewardsSummary,
            price = price,
            existingUserRewardAmount = user.rewardAmount ?: 0,
            defaultMerchantRewardRate = merchant.defaultEarnedRewardsRate,
        )
    }

    suspend fun fetchEarnedRewardSummary(
        price: Int,
        items: List<Item>?,
        userCohorts: List<String>?,
        merchant: Merchant,
        user: PublicUserData,
    ): EarnedRewardsSummary? {
        if (!merchant.enableConfigurableRewards) {
            return EarnedRewardsSummary.generateLocally(
                merchant = merchant,
                price = price,
                userRewardAmount = user.rewardAmount ?: 0,
                firstPurchaseBonusEligibility = user.firstPurchaseBonusEligibility
            )
        }

        val amountEligible = (price - (user.rewardAmount ?: 0)).coerceAtLeast(0)
        val response = transactionsSvcClient.calculateEarnedRewards(
            merchantId = merchant.id,
            amountEligibleForEarnedRewards = amountEligible,
            isNewCatchUser = user.firstPurchaseBonusEligibility,
            items = items,
            userCohorts = userCohorts,
        )
        return when (response) {
            is NetworkResponse.Success -> response.body
            is NetworkResponse.Failure -> {
                Log.e("RewardsRepositoryImpl", response.message, response.error)
                null
            }
        }
    }

    private fun getPrioritizedReward(
        earnedRewardsSummary: EarnedRewardsSummary,
        price: Int,
        existingUserRewardAmount: Int,
        defaultMerchantRewardRate: Double
    ): CalculatedReward {
        // Take the larger of the rewards rates between
        // the default merchant rate and the rate returned in the rewards summary
        val effectiveRewardRate =
            max(earnedRewardsSummary.percentageRewardRate, defaultMerchantRewardRate)

        // If the purchase price is less than or equal to 0, fallback on the rewards rate.
        if (price <= 0) {
            return CalculatedReward.PercentRate(effectiveRewardRate)
        }

        val earnedRewardsTotal = earnedRewardsSummary.earnedRewardsTotal ?: 0
        val discountAmount = earnedRewardsSummary.signUpDiscountAmount
        val savedAmount = discountAmount + existingUserRewardAmount

        return when {
            // For the sign up discount experiment
            discountAmount > 0 -> CalculatedReward.Saved(min(savedAmount, price))

            // If the user has rewards to redeem, return redeemable credits up to the price amount
            existingUserRewardAmount > 0 -> CalculatedReward.RedeemableCredits(
                min(existingUserRewardAmount, price)
            )

            // Return the earned rewards only if earned rewards > 0.
            earnedRewardsTotal > 0 -> CalculatedReward.EarnedCredits(earnedRewardsTotal)

            // Otherwise fallback to the rewards rate
            else -> CalculatedReward.PercentRate(effectiveRewardRate)
        }
    }
}
