package com.getcatch.android.viewmodels

import androidx.lifecycle.ViewModel
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class CampaignLinkViewModel(
    private val transactionsSvcClient: TransactionsSvcClient,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CampaignLinkUiState>(CampaignLinkUiState.Loading)
    val uiState: StateFlow<CampaignLinkUiState> = _uiState.asStateFlow()

    suspend fun loadCampaign(campaignName: String) {
        val response = transactionsSvcClient.fetchRewardCampaign(campaignName)
        if (response is NetworkResponse.Success) {
            _uiState.value = CampaignLinkUiState.Success(response.body)
        }
    }
}
