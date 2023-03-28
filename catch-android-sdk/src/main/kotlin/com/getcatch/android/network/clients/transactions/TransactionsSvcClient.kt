package com.getcatch.android.network.clients.transactions

import com.getcatch.android.models.PublicUserData
import com.getcatch.android.network.NetworkResponse

internal interface TransactionsSvcClient {
    suspend fun fetchUserData(deviceToken: String, merchantId: String): NetworkResponse<PublicUserData>
}
