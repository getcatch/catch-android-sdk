package com.getcatch.android.clients.merchant

import com.getcatch.android.CATCH_BASE_URL
import com.getcatch.android.clients.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

internal object MerchantSvcClientImpl : MerchantSvcClient {
    private val httpClient by lazy { KtorClient }
    override suspend fun loadPublicMerchantData(
        publicKey: String
    ): LoadPublicMerchantDataResponse {
        return httpClient.get {
            url("$CATCH_BASE_URL/api/merchants-svc/merchants/public_keys/$publicKey/public")
        }.body()
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
