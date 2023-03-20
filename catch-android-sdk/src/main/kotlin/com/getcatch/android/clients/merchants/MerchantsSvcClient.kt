package com.getcatch.android.clients.merchants

internal interface MerchantsSvcClient {
    suspend fun loadPublicMerchantData(publicKey: String): LoadPublicMerchantDataResponse

    suspend fun getWidgetContent(
        orderTotal: Int? = null,
        userCohorts: List<String>? = null,
        items: List<String>? = null,
        deviceToken: String? = null,
        useConfigRewards: Boolean? = null,
    ): WidgetContentResponse
}
