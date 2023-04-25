package com.getcatch.android.utils

import com.getcatch.android.models.Merchant
import com.getcatch.android.models.MerchantAssetType
import com.getcatch.android.models.PublicKey
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.android.models.tofu.TofuPath
import com.getcatch.android.network.Environment

/**
 * Helper functions for generating the URLs for web views and assets
 */
internal object CatchUrls {
    private const val ASSETS_BASE_URL = "https://dev.assets.getcatch.com"

    fun assetUrl(merchantId: String, assetType: MerchantAssetType) =
        "$ASSETS_BASE_URL/merchant-assets/$merchantId/${assetType.fileName}"

    /**
     * A set of all of the urls we want to always be opened in an internal webview.
     */
    val internalWebViewBaseUrls
        get() = setOf(
            "${Environment.SANDBOX.baseUrl}/c/",
            "${Environment.SANDBOX.baseUrl}/t/",
            "${Environment.PRODUCTION.baseUrl}/c/",
            "${Environment.PRODUCTION.baseUrl}/t/",
        )

    fun tofu(environment: Environment, publicKey: PublicKey, merchant: Merchant, path: TofuPath) =
        buildUrl("${environment.baseUrl}/t/${path.path}") {
            parameter("publicKey", publicKey.value)
            parameter("merchantId", merchant.id)
            parameter("merchantName", merchant.name)
            parameter("credit", merchant.defaultEarnedRewardsRate.toPercentString())
            parameter("referer", environment.baseUrl)
            parameter("loadTheme", merchant.hasTheme.toString())
        }

    fun directCheckout(
        environment: Environment,
        publicKey: PublicKey,
        merchant: Merchant,
        checkoutId: String,
        prefill: CheckoutPrefill,
        hideHeader: Boolean = false,
    ) = buildUrl("${environment.baseUrl}/c/") {
        parameter("publicKey", publicKey.value)
        parameter("checkoutId", checkoutId)
        parameter("referer", environment.baseUrl)
        parameter("loadTheme", merchant.hasTheme.toString())
        parameter("hideHeader", hideHeader.toString())
        parameter("prefillUserPhone", prefill.userPhone)
        parameter("prefillUserName", prefill.userName)
        parameter("prefillUserEmail", prefill.userEmail)
        parameter("flow", "iframe")
    }

    fun virtualCardCheckout(
        environment: Environment,
        publicKey: PublicKey,
        merchant: Merchant,
        orderId: String,
        prefill: CheckoutPrefill,
        hideHeader: Boolean = false,
    ) = buildUrl("${environment.baseUrl}/c/") {
        parameter("publicKey", publicKey.value)
        parameter("orderId", orderId)
        parameter("referer", environment.baseUrl)
        parameter("loadTheme", merchant.hasTheme.toString())
        parameter("hideHeader", hideHeader.toString())
        parameter("prefillUserPhone", prefill.userPhone)
        parameter("prefillUserName", prefill.userName)
        parameter("prefillUserEmail", prefill.userEmail)
        parameter("flow", "iframe")
    }
}
