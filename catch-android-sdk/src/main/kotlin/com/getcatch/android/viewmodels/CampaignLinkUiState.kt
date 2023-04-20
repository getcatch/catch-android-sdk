package com.getcatch.android.viewmodels

import com.getcatch.android.models.RewardCampaign

internal sealed interface CampaignLinkUiState {
    object Loading : CampaignLinkUiState
    class Success(val rewardCampaign: RewardCampaign) : CampaignLinkUiState
}
