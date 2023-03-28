package com.getcatch.android.network.clients.merchants

import com.getcatch.android.models.Merchant
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.utils.handleNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class MerchantsSvcClientImpl(
    private val httpClient: HttpClient,
    environment: Environment
) : MerchantsSvcClient {
    internal val baseUrl = "https://${environment.host}/api/merchants-svc"
    override suspend fun loadPublicMerchantData(
        publicKey: PublicKey
    ): NetworkResponse<Merchant> = handleNetworkResponse {
        httpClient.get("$baseUrl/merchants/public_keys/${publicKey.value}/public")
    }
}
