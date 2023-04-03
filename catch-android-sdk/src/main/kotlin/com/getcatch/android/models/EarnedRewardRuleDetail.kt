package com.getcatch.android.models

import kotlinx.serialization.Serializable
import kotlin.math.ceil

@Serializable
internal data class EarnedRewardRuleDetail(
    val rewardRuleId: String,
    val earnedRewardAmount: Int,
    val userFacingName: String?,
    val sortOrder: Int?,
    val detailsLink: String?,
    val rewardAmountType: String?,
    val ruleEngineType: String?,
    val flatRewardAmount: Int?,
    val percentageRewardRate: Double?,
) {
    companion object {
        fun unrestrictedEarned(
            merchant: Merchant,
            price: Int,
            userRewardAmount: Int,
        ): EarnedRewardRuleDetail {
            val amountEligibleForEarnedRewards = (price - userRewardAmount).coerceAtLeast(0)
            val unrestrictedEarnedRewardAmount =
                ceil(amountEligibleForEarnedRewards.toDouble() * merchant.defaultEarnedRewardsRate).toInt()

            return EarnedRewardRuleDetail(
                rewardRuleId = merchant.unrestrictedRewardRuleId,
                earnedRewardAmount = unrestrictedEarnedRewardAmount,
                userFacingName = RewardType.UNRESTRICTED_REWARD_RULE_USER_FACING_NAME,
                rewardAmountType = RewardType.PERCENTAGE_REWARD_AMOUNT_TYPE,
                ruleEngineType = RewardType.UNRESTRICTED_RULE_ENGINE_TYPE,
                percentageRewardRate = merchant.defaultEarnedRewardsRate,
                flatRewardAmount = null,
                sortOrder = null,
                detailsLink = null,
            )
        }

        fun newCatchUser(merchant: Merchant): EarnedRewardRuleDetail? {
            if (merchant.newCatchUserRewardRuleId == null) return null
            val signUpBonus = merchant.defaultSignUpBonus.toInt()
            return EarnedRewardRuleDetail(
                rewardRuleId = merchant.newCatchUserRewardRuleId,
                earnedRewardAmount = signUpBonus,
                userFacingName = RewardType.NEW_CATCH_USER_REWARD_RULE_USER_FACING_NAME,
                rewardAmountType = RewardType.FLAT_REWARD_AMOUNT_TYPE,
                ruleEngineType = RewardType.NEW_CATCH_USER_RULE_ENGINE_TYPE,
                flatRewardAmount = signUpBonus,
                percentageRewardRate = null,
                sortOrder = null,
                detailsLink = null,
            )
        }
    }
}
