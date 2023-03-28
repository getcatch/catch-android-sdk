package com.getcatch.android.repository

import com.getcatch.android.cache.CacheManager
import com.getcatch.android.models.Merchant
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.merchants.MerchantsSvcClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class MerchantRepositoryImpl(
    val merchantsSvcClient: MerchantsSvcClient,
    val cache: CacheManager,
    val publicKey: PublicKey,
) : MerchantRepository {
    private val _activeMerchant = MutableStateFlow(cache.merchant)
    override val activeMerchant: StateFlow<Merchant?> = _activeMerchant.asStateFlow()

    override suspend fun loadMerchant() {
        val response = merchantsSvcClient.loadPublicMerchantData(
            publicKey = publicKey
        )
        if (response is NetworkResponse.Success) {
            _activeMerchant.value = response.body
            cache.merchant = response.body
        }
    }
}
