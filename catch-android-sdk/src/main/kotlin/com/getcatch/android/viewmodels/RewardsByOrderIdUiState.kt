package com.getcatch.android.viewmodels

import com.getcatch.android.models.RewardsForConfirmedCheckout

internal sealed interface RewardsByOrderIdUiState {
    object Loading: RewardsByOrderIdUiState
    class Success(val rewards: RewardsForConfirmedCheckout): RewardsByOrderIdUiState
}
