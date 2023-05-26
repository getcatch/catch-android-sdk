package com.getcatch.android.viewmodels

import androidx.lifecycle.ViewModel
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import com.getcatch.android.utils.CatchUrls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class CampaignLinkViewModel(
    private val transactionsSvcClient: TransactionsSvcClient,
) : ViewModel(), KoinComponent {
    private val environment: Environment by inject()
    private val _uiState = MutableStateFlow<CampaignLinkUiState>(CampaignLinkUiState.Loading)
    val uiState: StateFlow<CampaignLinkUiState> = _uiState.asStateFlow()

    suspend fun loadCampaign(campaignName: String) {
        val response = transactionsSvcClient.fetchRewardCampaign(campaignName)
        if (response is NetworkResponse.Success) {
            val campaignUrl = CatchUrls.rewardCampaign(environment, response.body.rewardCampaignId)
            _uiState.value = CampaignLinkUiState.Success(
                rewardCampaign = response.body,
                campaignUrl = campaignUrl,
            )
        }
    }
}
