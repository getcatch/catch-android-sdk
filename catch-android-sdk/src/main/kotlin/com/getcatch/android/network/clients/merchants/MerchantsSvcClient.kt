package com.getcatch.android.network.clients.merchants

import com.getcatch.android.models.Merchant
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.NetworkResponse

internal interface MerchantsSvcClient {
    suspend fun loadPublicMerchantData(publicKey: PublicKey): NetworkResponse<Merchant>
}
