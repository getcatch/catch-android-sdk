package com.getcatch.android.models

internal data class CalculateRewardsResult(
    val reward: CalculatedReward,
    val rewardsSummary: EarnedRewardsSummary?,
) {
    companion object {
        /** Fallback for when no merchant is loaded. */
        val NoRewardsSummary = CalculateRewardsResult(
            reward = CalculatedReward.Default,
            rewardsSummary = null,
        )

        /** Fallback for when no rewards summary can be loaded/generated. */
        fun merchantDefaultRewardSummary(merchant: Merchant) = CalculateRewardsResult(
            reward = CalculatedReward.PercentRate(merchant.defaultEarnedRewardsRate),
            rewardsSummary = null,
        )
    }
}
