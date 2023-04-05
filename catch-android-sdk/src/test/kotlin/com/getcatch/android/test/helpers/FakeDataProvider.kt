package com.getcatch.android.test.helpers

import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.Item
import com.getcatch.android.models.Merchant
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.models.checkout.Price

internal object FakeDataProvider {
    private const val fakeMerchantCardBackgroundImageUrl =
        "https://dev.assets.getcatch.com/merchant-assets/humans-s768ng/card_background.png"

    val merchant = Merchant(
        id = "fake-merchant-id",
        name = "Fake Merchant",
        url = "https://getcatch.com",
        defaultEarnedRewardsRate = 0.11,
        enableConfigurableRewards = true,
        rewardsLifetimeInDays = 180,
        cardBackgroundImageUrl = fakeMerchantCardBackgroundImageUrl,
        cardBackgroundColor = "#C779D0",
        cardFontColor = "#FFFFFF",
        donationRecipient = null,
        defaultSignUpBonus = 1000.0,
        defaultSignUpDiscount = 0.0,
        unrestrictedRewardRuleId = "unrestrictedId",
        newCatchUserRewardRuleId = "newCatchUserId",
        theme = null
    )

    val user = PublicUserData(
        userFirstName = "Jimmy",
        rewardAmount = 1000,
        firstPurchaseBonusEligibility = false,
        isCatchEmployee = false,
        wasReferred = false,
    )

    fun randomItem() = Item(
        name = "item-${randomString()}",
        sku = "sku-${randomString()}",
        price = Price(
            amount = 10,
            currency = "USD",
        ),
        quantity = 1,
        category = listOf(),
        imageUrl = ""
    )

    object User {
        val NoCredits = PublicUserData(
            userFirstName = "First",
            rewardAmount = 0,
            firstPurchaseBonusEligibility = false
        )

        val Returning = PublicUserData(
            userFirstName = "First",
            rewardAmount = 1500,
            firstPurchaseBonusEligibility = false
        )

        val New = PublicUserData(
            userFirstName = "First",
            rewardAmount = 0,
            firstPurchaseBonusEligibility = true
        )

        fun newWithCredits(amount: Int = 500) = PublicUserData(
            userFirstName = "First",
            rewardAmount = amount,
            firstPurchaseBonusEligibility = true
        )

    }

    object EarnedRewards {
        fun default(
            price: Int,
            rewardRate: Double = merchant.defaultEarnedRewardsRate
        ) = EarnedRewardsSummary(
            signUpBonusAmount = 0,
            signUpDiscountAmount = 0,
            percentageRewardRate = rewardRate,
            earnedRewardsTotal = (price * rewardRate).toInt(),
            earnedRewardBreakdown = emptyList()
        )

        fun newUser(
            price: Int,
            rewardRate: Double = merchant.defaultEarnedRewardsRate,
            signUpBonus: Int = 1000
        ) = EarnedRewardsSummary(
            signUpBonusAmount = signUpBonus,
            signUpDiscountAmount = 0,
            percentageRewardRate = rewardRate,
            earnedRewardsTotal = (price * rewardRate).toInt() + signUpBonus,
            earnedRewardBreakdown = emptyList()
        )

        fun signUpDiscount(
            price: Int,
            rewardRate: Double = merchant.defaultEarnedRewardsRate,
            signUpDiscount: Int = 1000
        ) = EarnedRewardsSummary(
            signUpBonusAmount = 0,
            signUpDiscountAmount = signUpDiscount,
            percentageRewardRate = rewardRate,
            earnedRewardsTotal = (price * rewardRate).toInt(),
            earnedRewardBreakdown = emptyList()
        )

        fun noRewards(rewardRate: Double = merchant.defaultEarnedRewardsRate) =
            EarnedRewardsSummary(
                signUpBonusAmount = 0,
                signUpDiscountAmount = 0,
                percentageRewardRate = rewardRate,
                earnedRewardsTotal = 0,
                earnedRewardBreakdown = emptyList()
            )
    }
}
