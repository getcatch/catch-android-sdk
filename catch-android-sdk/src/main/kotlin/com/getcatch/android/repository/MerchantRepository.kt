package com.getcatch.android.repository

import com.getcatch.android.domain.Merchant
import kotlinx.coroutines.flow.StateFlow

internal interface MerchantRepository {
    val activeMerchant: StateFlow<Merchant?>
    suspend fun loadMerchant(publicKey: String)
}
