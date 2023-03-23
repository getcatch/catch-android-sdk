package com.getcatch.android.test.mocks

import com.getcatch.android.models.Merchant
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.merchants.MerchantsSvcClient

internal class MockMerchantsSvcClient(
    private var loadPublicMerchantDataResponseMap: Map<PublicKey, NetworkResponse<Merchant>> = mapOf()
) : MerchantsSvcClient {
    override suspend fun loadPublicMerchantData(publicKey: PublicKey) =
        loadPublicMerchantDataResponseMap[publicKey]!!
}
