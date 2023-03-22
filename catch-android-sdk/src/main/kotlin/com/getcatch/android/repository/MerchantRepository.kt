package com.getcatch.android.repository

import com.getcatch.android.models.Merchant
import kotlinx.coroutines.flow.StateFlow

internal interface MerchantRepository {
    val activeMerchant: StateFlow<Merchant?>
    suspend fun loadMerchant()
}
