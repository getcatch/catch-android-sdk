package com.getcatch.android.models

import com.getcatch.android.utils.CatchUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Merchant(
    /** The merchant's unique identifier */
    @SerialName("merchant_id")
    val id: String,

    /** Name to show in checkout flow, merchant cards, etc. */
    val name: String,

    /** URL (base and path only) to use when directing a user to shop at this merchant. */
    val url: String,

    /** Between 0 and 1, the fraction of amount paid that a user gets back at this merchant. */
    val defaultEarnedRewardsRate: Double,

    /** Flag determining if merchant uses configurable rewards. */
    val enableConfigurableRewards: Boolean,

    /** Number of days after earning that the rewards expire. */
    val rewardsLifetimeInDays: Int,

    /** Full URL for the merchant card background image */
    val cardBackgroundImageUrl: String?,

    /** Hexadecimal string representing the background color for the merchant card. ex. #FFFFFF */
    val cardBackgroundColor: String,

    /** Hexadecimal string representing the font color for the merchant card. ex. #000000 */
    val cardFontColor: String,

    /** The recipient of donation campaigns. */
    val donationRecipient: DonationRecipient?,

    /** Flat amount that a new Catch user will receive in rewards at this merchant */
    val defaultSignUpBonus: Double,

    /** Flat amount that a new Catch user will receive as a discount at this merchant */
    val defaultSignUpDiscount: Double,

    /** ID corresponding to the currently active unrestricted reward rule with the highest percentage */
    val unrestrictedRewardRuleId: String,

    /** ID corresponding to the currently active new catch user reward rule with the highest amount */
    val newCatchUserRewardRuleId: String?,

    /** Theme for web views */
    val theme: MerchantThemeConfig?,
) {
    /** Generates url for merchant logo image asset */
    val cardLogoImageUrl: String
        get() = CatchUrls.assetUrl(id, MerchantAssetType.CARD_LOGO)

    val hasTheme: Boolean
        get() = theme != null
}
