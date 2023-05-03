package com.getcatch.android.viewmodels

import androidx.lifecycle.ViewModel
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class RewardsByOrderIdViewModel(
    private val transactionsSvcClient: TransactionsSvcClient,
    private val publicKey: PublicKey
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<RewardsByOrderIdUiState>(RewardsByOrderIdUiState.Loading)
    val uiState: StateFlow<RewardsByOrderIdUiState> = _uiState.asStateFlow()

    suspend fun loadRewards(orderId: String) {
        val response = transactionsSvcClient.fetchRewardsForConfirmedCheckout(orderId, publicKey)
        if (response is NetworkResponse.Success) {
            _uiState.value = RewardsByOrderIdUiState.Success(response.body)
        }
    }
}
