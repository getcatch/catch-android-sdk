package com.getcatch.android.viewmodels

import com.getcatch.android.models.CalculateRewardsResult

internal sealed interface EarnRedeemUiState {
    object Loading : EarnRedeemUiState
    class Success(calculateRewardsResult: CalculateRewardsResult) : EarnRedeemUiState {
        val reward = calculateRewardsResult.reward
        val summary = calculateRewardsResult.rewardsSummary
    }
}
