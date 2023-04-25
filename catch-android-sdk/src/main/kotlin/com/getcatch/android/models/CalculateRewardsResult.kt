package com.getcatch.android.models

internal data class CalculateRewardsResult(
    val reward: CalculatedReward,
    val rewardsSummary: EarnedRewardsSummary?,
) {
    companion object {
        val NoRewardsSummary = CalculateRewardsResult(
            reward = CalculatedReward.Default,
            rewardsSummary = null,
        )
    }
}
