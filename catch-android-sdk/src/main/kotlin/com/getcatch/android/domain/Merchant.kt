package com.getcatch.android.domain

import com.getcatch.android.utils.CatchUrls

internal data class Merchant(
    val id: String,
    val name: String,
    val rewardsRatePercent: Float,
    val url: String,
    val rewardsLifetimeInDays: Int,
    val cardBackgroundImageUrl: String? = null,
    val cardBackgroundColor: String,
    val cardFontColor: String,
    val hasTheme: Boolean,
    val donationRecipient: DonationRecipient? = null,
) {
    val cardLogoImageUrl: String
        get() = CatchUrls.assetUrl(id, MerchantAssetType.CARD_LOGO)
}
