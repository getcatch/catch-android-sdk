package com.getcatch.android.domain

import com.getcatch.android.utils.CatchUrls

internal data class Merchant(
    val id: String,
    val publicKey: String,
    val name: String,
    val rewardsRatePercent: Float,
    val url: String,
    val rewardsLifetimeInDays: Int,
    val cardBackground: String,
    val cardBackgroundImageUrl: String? = null,
    val cardBackgroundColor: String? = null,
    val cardFontColor: String,
    val hasTheme: Boolean,
) {
    val cardLogoImageUrl: String
        get() = CatchUrls.assetUrl(id, MerchantAssetType.CARD_LOGO)
}
