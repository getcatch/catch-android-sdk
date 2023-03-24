package com.getcatch.android.network.clients.transactions

import com.getcatch.android.models.PublicUserData
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.utils.handleNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class TransactionsSvcClientImpl(
    private val httpClient: HttpClient,
    environment: Environment
) : TransactionsSvcClient {
    val baseUrl = "https://${environment.host}/api/transactions-svc"
    override suspend fun fetchUserData(
        deviceToken: String,
        merchantId: String
    ): NetworkResponse<PublicUserData> = handleNetworkResponse {
        httpClient.get(
            "$baseUrl/user_devices/$deviceToken/user_data"

        ) {
            parameter("merchant_id", merchantId)
        }
    }
}
