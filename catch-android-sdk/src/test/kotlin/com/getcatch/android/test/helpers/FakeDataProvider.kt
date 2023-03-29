package com.getcatch.android.test.helpers

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
        defaultEarnedRewardsRate = 0.1,
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
}
