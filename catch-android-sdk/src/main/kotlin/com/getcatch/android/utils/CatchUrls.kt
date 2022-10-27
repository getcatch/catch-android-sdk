package com.getcatch.android.utils

import com.getcatch.android.domain.Merchant
import com.getcatch.android.domain.MerchantAssetType
import com.getcatch.android.domain.PublicKey

/**
 * Helper functions for generating the different URLs needed throughout the SDK
 */
internal object CatchUrls {
    const val CATCH_BASE_URL = "https://dev.app-sandbox.getcatch.com"
    private const val ASSETS_BASE_URL = "https://dev.assets.getcatch.com"

    fun eduModal(publicKey: PublicKey, merchant: Merchant) = buildUrl("$CATCH_BASE_URL/t/") {
        parameter("publicKey", publicKey.value)
        parameter("merchantId", merchant.id)
        parameter("merchantName", merchant.name)
        parameter("credit", merchant.rewardsRatePercent.toString())
        parameter("referer", CATCH_BASE_URL)
        parameter("loadTheme", merchant.hasTheme.toString())
    }

    fun rewardsBreakdownModal(publicKey: PublicKey, merchant: Merchant) =
        buildUrl("$CATCH_BASE_URL/t/breakdown/") {
            parameter("publicKey", publicKey.value)
            parameter("merchantId", merchant.id)
            parameter("merchantName", merchant.name)
            parameter("credit", merchant.rewardsRatePercent.toString())
            parameter("referer", CATCH_BASE_URL)
            parameter("loadTheme", merchant.hasTheme.toString())
        }

    fun checkout(
        publicKey: PublicKey,
        merchant: Merchant,
        checkoutId: String,
        prefillUserPhone: String = "",
        prefillUserName: String = "",
        prefillUserEmail: String = "",
        hideHeader: Boolean = false,
    ) = buildUrl("$CATCH_BASE_URL/c/") {
        parameter("publicKey", publicKey.value)
        parameter("checkoutId", checkoutId)
        parameter("referer", CATCH_BASE_URL)
        parameter("loadTheme", merchant.hasTheme.toString())
        parameter("hideHeader", hideHeader.toString())
        parameter("prefillUserPhone", prefillUserPhone)
        parameter("prefillUserName", prefillUserName)
        parameter("prefillUserEmail", prefillUserEmail)
    }

    fun assetUrl(merchantId: String, assetType: MerchantAssetType) =
        "$ASSETS_BASE_URL/merchant-assets/$merchantId/${assetType.fileName}"

    /**
     * A set of all of the urls we want to always be opened in an internal webview.
     */
    val internalWebViewBaseUrls = setOf(
        "$CATCH_BASE_URL/c/",
        "$CATCH_BASE_URL/t/",
    )
}
