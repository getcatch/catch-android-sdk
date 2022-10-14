package com.getcatch.android.utils

import com.getcatch.android.domain.Merchant
import com.getcatch.android.domain.MerchantAssetType

/**
 * Helper functions for generating the different URLs needed throughout the SDK
 */
internal object CatchUrls {
    private const val CATCH_BASE_URL = "https://dev.app-sandbox.getcatch.com"
    private const val ASSETS_BASE_URL = "https://dev.assets.getcatch.com"

    fun eduModal(merchant: Merchant) = buildUrl("$CATCH_BASE_URL/t/") {
        parameters.apply {
            append("merchantId", merchant.id)
            append("merchantName", merchant.name)
            append("credit", merchant.rewardsRatePercent.toString())
            append("referer", CATCH_BASE_URL)
            append("publicKey", merchant.publicKey)
            append("loadTheme", merchant.hasTheme.toString())
        }
    }

    fun rewardsBreakdownModal(merchant: Merchant) = buildUrl("$CATCH_BASE_URL/t/breakdown/") {
        parameters.apply {
            append("merchantId", merchant.id)
            append("merchantName", merchant.name)
            append("credit", merchant.rewardsRatePercent.toString())
            append("referer", CATCH_BASE_URL)
            append("publicKey", merchant.publicKey)
            append("loadTheme", merchant.hasTheme.toString())
        }
    }

    fun publicMerchantData(publicKey: String) =
        "$CATCH_BASE_URL/api/merchants-svc/merchants/public_keys/$publicKey/public"

    fun checkout(
        merchant: Merchant,
        checkoutId: String,
        prefillUserPhone: String = "",
        prefillUserName: String = "",
        prefillUserEmail: String = "",
        hideHeader: Boolean = false,
    ) = buildUrl("$CATCH_BASE_URL/c/") {
        parameters.apply {
            append("checkoutId", checkoutId)
            append("referer", CATCH_BASE_URL)
            append("publicKey", merchant.publicKey)
            append("loadTheme", merchant.hasTheme.toString())
            append("hideHeader", hideHeader.toString())
            append("prefillUserPhone", prefillUserPhone)
            append("prefillUserName", prefillUserName)
            append("prefillUserEmail", prefillUserEmail)
        }
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
