package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class EarnedRewardsSummary(
    /** Amount of rewards (in US cents) that the user can earn as a sign up bonus. */
    val signUpBonusAmount: Int,

    /** Amount of rewards (in US cents) that the user can earn as a sign up discount. */
    val signUpDiscountAmount: Int,

    /** Between 0 and 1, the fraction of amount paid that a user earns back from their purchase. */
    val percentageRewardRate: Double,

    /** Amount of rewards (in US cents) that the user can earn. */
    val earnedRewardsTotal: Int?,

    /** Rewards rules used for the earnings breakdown modal */
    val earnedRewardBreakdown: List<EarnedRewardRuleDetail>? = null,
) {
    companion object {
        fun generateLocally(
            merchant: Merchant,
            price: Int,
            userRewardAmount: Int,
            firstPurchaseBonusEligibility: Boolean,
        ): EarnedRewardsSummary {
            val unrestrictedEarnedRewardBreakdown = EarnedRewardRuleDetail.unrestrictedEarned(
                merchant = merchant,
                price = price,
                userRewardAmount = userRewardAmount
            )

            val earnedRewardBreakdown: List<EarnedRewardRuleDetail> = buildList {
                add(unrestrictedEarnedRewardBreakdown)
                if (firstPurchaseBonusEligibility) {
                    EarnedRewardRuleDetail.newCatchUser(merchant)?.let {
                        add(it)
                    }
                }
            }

            val earnedRewardsTotal = earnedRewardBreakdown.sumOf { it.earnedRewardAmount }
            val signUpBonusAmount =
                if (firstPurchaseBonusEligibility) merchant.defaultSignUpBonus.toInt()
                else 0

            return EarnedRewardsSummary(
                signUpBonusAmount = signUpBonusAmount,
                // Leave as zero until we figure out how to enable experiments on mobile
                // This experiment is currently disabled, so not an issue yet
                signUpDiscountAmount = 0,
                percentageRewardRate = merchant.defaultEarnedRewardsRate,
                earnedRewardsTotal = earnedRewardsTotal,
                earnedRewardBreakdown = earnedRewardBreakdown,
            )
        }
    }
}
