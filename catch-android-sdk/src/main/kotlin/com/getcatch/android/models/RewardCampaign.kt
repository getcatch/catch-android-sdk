package com.getcatch.android.models

import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.toDate
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
internal data class RewardCampaign(
    /** The unique identifier used to navigate to the reward campaign URL. */
    val rewardCampaignId: String,

    /** Amount of the total reward in cents, must be greater than 0. */
    val totalAmount: Double,

    /** The date when the rewards expire. */
    val rewardsExpiration: Instant,

    /** Full URL for the merchant card background image */
    val merchantCardBackgroundImageUrl: String?,

    /** Hexadecimal string representing the background color for the merchant card. ex. #FFFFFF */
    val merchantCardBackgroundColor: String,

    /** Hexadecimal string representing the font color for the merchant card. ex. #000000 */
    val merchantCardFontColor: String,

    /** The merchant's unique identifier */
    val merchantId: String,

    /** Name to show in checkout flow, merchant cards, etc. */
    val merchantName: String,
) {
    /** Generates url for merchant logo image asset */
    val cardLogoImageUrl: String = CatchUrls.assetUrl(merchantId, MerchantAssetType.CARD_LOGO)

    val amountInDollars: String = totalAmount.toInt().centsToDollarsString()

    val expirationDate: LocalDate = rewardsExpiration.toDate()
}
