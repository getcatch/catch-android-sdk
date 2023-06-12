package com.getcatch.android.repository

import android.util.Log
import com.getcatch.android.models.CalculateRewardsResult
import com.getcatch.android.models.CalculatedAvailableRewardsBreakdown
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
    ): CalculateRewardsResult {
        val merchant =
            merchantRepo.activeMerchant.value ?: return CalculateRewardsResult.NoRewardsSummary

        val calculatedAvailableRewardsBreakdown =
            CalculatedAvailableRewardsBreakdown.calculate(
                price,
                user.availableRewardBreakdown ?: emptyList()
            )

        val earnedRewardsSummary = fetchEarnedRewardSummary(
            price = price,
            items = items,
            userCohorts = userCohorts,
            merchant = merchant,
            redeemableRewardsTotal = calculatedAvailableRewardsBreakdown.redeemableRewardsTotal,
            firstPurchaseBonusEligibility = user.firstPurchaseBonusEligibility,
        ) ?: return CalculateRewardsResult.merchantDefaultRewardSummary(merchant)

        val calculatedReward = getPrioritizedReward(
            earnedRewardsSummary = earnedRewardsSummary,
            price = price,
            defaultMerchantRewardRate = merchant.defaultEarnedRewardsRate,
            calculatedAvailableRewardsBreakdown = calculatedAvailableRewardsBreakdown,
        )
        return CalculateRewardsResult(
            reward = calculatedReward,
            rewardsSummary = earnedRewardsSummary,
        )
    }

    suspend fun fetchEarnedRewardSummary(
        price: Int,
        items: List<Item>?,
        userCohorts: List<String>?,
        merchant: Merchant,
        redeemableRewardsTotal: Int,
        firstPurchaseBonusEligibility: Boolean,
    ): EarnedRewardsSummary? {
        if (!merchant.enableConfigurableRewards) {
            return EarnedRewardsSummary.generateLocally(
                merchant = merchant,
                price = price,
                userRewardAmount = redeemableRewardsTotal,
                firstPurchaseBonusEligibility = firstPurchaseBonusEligibility
            )
        }

        val amountEligible = (price - (redeemableRewardsTotal)).coerceAtLeast(0)
        val response = transactionsSvcClient.calculateEarnedRewards(
            merchantId = merchant.id,
            amountEligibleForEarnedRewards = amountEligible,
            isNewCatchUser = firstPurchaseBonusEligibility,
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
        calculatedAvailableRewardsBreakdown: CalculatedAvailableRewardsBreakdown,
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

        val availableRewardsAmount = calculatedAvailableRewardsBreakdown.redeemableRewardsTotal

        val earnedRewardsTotal = earnedRewardsSummary.earnedRewardsTotal ?: 0
        val discountAmount = earnedRewardsSummary.signUpDiscountAmount
        val savedAmount = discountAmount + availableRewardsAmount

        return when {
            // For the sign up discount experiment
            discountAmount > 0 -> CalculatedReward.Saved(min(savedAmount, price))

            // If the user has rewards to redeem, return redeemable credits up to the price amount
            availableRewardsAmount > 0 -> CalculatedReward.RedeemableCredits(
                min(availableRewardsAmount, price)
            )

            // Return the earned rewards only if earned rewards > 0.
            earnedRewardsTotal > 0 -> CalculatedReward.EarnedCredits(earnedRewardsTotal)

            // Otherwise fallback to the rewards rate
            else -> CalculatedReward.PercentRate(effectiveRewardRate)
        }
    }
}
