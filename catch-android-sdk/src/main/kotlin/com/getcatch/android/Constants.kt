package com.getcatch.android

import android.net.Uri
import com.getcatch.android.domain.Merchant

internal const val CATCH_BASE_URL = "https://dev.app-sandbox.getcatch.com"
internal const val LOCAL_URL = "http://10.0.2.2:3000"
internal const val ASSETS_BASE_URL = "https://dev.assets.getcatch.com"

internal object CatchUrls {
    val testFrame = "$LOCAL_URL/frame"
    fun eduModal(merchant: Merchant) = Uri.parse("$CATCH_BASE_URL/t/")
        .buildUpon()
        .appendQueryParameter("merchantId", merchant.id)
        .appendQueryParameter("merchantName", merchant.name)
        .appendQueryParameter("credit", merchant.rewardsRatePercent.toString())
        .appendQueryParameter("referer", CATCH_BASE_URL)
        .appendQueryParameter("publicKey", merchant.publicKey)
//        .appendQueryParameter("loadTheme", merchant.hasTheme.toString())
        .build()
        .toString()

    fun rewardsBreakdownModal(merchant: Merchant) = Uri.parse("$LOCAL_URL/m/t/breakdown")
        .buildUpon()
        .appendQueryParameter("merchantId", merchant.id)
        .appendQueryParameter("merchantName", merchant.name)
        .appendQueryParameter("credit", merchant.rewardsRatePercent.toString())
        .appendQueryParameter("referer", LOCAL_URL)
        .appendQueryParameter("publicKey", merchant.publicKey)
//        .appendQueryParameter("loadTheme", merchant.hasTheme.toString())
        .build()
        .toString()

    fun assetUrl(merchantId: String, assetType: MerchantAssetType) =
        "$ASSETS_BASE_URL/merchant-assets/$merchantId/${assetType.value}"
}

internal enum class MerchantAssetType(val value: String) {
    CARD_LOGO("card_logo.png")
}
