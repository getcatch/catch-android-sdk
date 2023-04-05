package com.getcatch.android.viewmodels

import com.getcatch.android.models.CalculatedReward

internal sealed interface EarnRedeemUiState {
    object Loading: EarnRedeemUiState
    class Success(val reward: CalculatedReward): EarnRedeemUiState
}
