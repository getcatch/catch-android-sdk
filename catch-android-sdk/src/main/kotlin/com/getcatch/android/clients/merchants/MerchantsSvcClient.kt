package com.getcatch.android.clients.merchants

import com.getcatch.android.domain.PublicKey

internal interface MerchantsSvcClient {
    suspend fun loadPublicMerchantData(publicKey: PublicKey): LoadPublicMerchantDataResponse

    suspend fun getWidgetContent(
        orderTotal: Int? = null,
        userCohorts: List<String>? = null,
        items: List<String>? = null,
        deviceToken: String? = null,
        useConfigRewards: Boolean? = null,
    ): WidgetContentResponse
}
