package com.getcatch.android.clients.merchant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class LoadPublicMerchantDataResponse(
    @SerialName("merchant_id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("rewards_rate")
    val rewardsRatePercent: Float,

    @SerialName("url")
    val url: String,

    @SerialName("rewards_lifetime_in_days")
    val rewardsLifetimeInDays: Int,

    @SerialName("card_background")
    val cardBackground: String,

    @SerialName("card_font_color")
    val cardFontColor: String,

    @SerialName("theme")
    val theme: JsonObject?,
)

@Serializable
internal data class CheckoutEarnedRewardRuleDetail(
    @SerialName("user_facing_name")
    val userFacingName: String,

    @SerialName("sort_order")
    val sortOrder: Int?,

    @SerialName("details_link")
    val detailsLink: String?,

    @SerialName("reward_amount_type")
    val rewardAmountType: String,

    @SerialName("rule_engine_type")
    val ruleEngineType: String,

    // Depending on the rewardAmountType, the corresponding field below will be required accordingly
    @SerialName("flat_reward_amount")
    val flatRewardAmount: Int?,

    @SerialName("percentage_reward_rate")
    val percentageRewardRate: Float?,
)

@Serializable
internal data class WidgetContentResponse(
    @SerialName("earned_sign_up_bonus_amount")
    val earnedSignUpBonusAmount: Int,

    @SerialName("earned_sign_up_discount_amount")
    val earnedSignUpDiscountAmount: Int,

    @SerialName("earned_rewards_total")
    val earnedRewardsTotal: Int?,

    @SerialName("earned_reward_breakdown")
    val earnedRewardBreakdown: List<CheckoutEarnedRewardRuleDetail>? = null,

    @SerialName("items")
    val items: List<String>? = null,

    @SerialName("device_token")
    val deviceToken: String? = null,

    @SerialName("use_config_rewards")
    val useConfigRewards: Boolean? = null,
)
