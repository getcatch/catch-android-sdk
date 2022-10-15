package com.getcatch.android.clients.merchants

import com.getcatch.android.utils.CatchUrls
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

internal class MerchantsSvcClientImpl(private val httpClient: HttpClient) : MerchantsSvcClient {
    override suspend fun loadPublicMerchantData(
        publicKey: String
    ): LoadPublicMerchantDataResponse {
        return httpClient.get(CatchUrls.publicMerchantData(publicKey)).body()
    }

    override suspend fun getWidgetContent(
        orderTotal: Int?,
        userCohorts: List<String>?,
        items: List<String>?,
        deviceToken: String?,
        useConfigRewards: Boolean?,
    ): WidgetContentResponse {
        return httpClient.get {
            url("https://catch-clark-cancel.free.beeceptor.com/get-widget-content")
            parameter("order_total", orderTotal)
            parameter("user_cohorts", userCohorts)
            parameter("items", items)
            parameter("device_token", deviceToken)
            parameter("use_config_rewards", useConfigRewards)
        }.body()
    }
}
