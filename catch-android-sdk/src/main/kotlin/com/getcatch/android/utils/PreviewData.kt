package com.getcatch.android.utils

import com.getcatch.android.models.DonationRecipient
import com.getcatch.android.models.Merchant

internal object PreviewData {
    val merchant = Merchant(
        id = "humans-s768ng",
        name = "Humans",
        url = "humans-dev.myshopify.com",
        defaultEarnedRewardsRate = 0.1,
        enableConfigurableRewards = true,
        rewardsLifetimeInDays = 180,
        cardBackgroundColor = "#C779D0",
        cardBackgroundImageUrl = "https://dev.assets.getcatch.com/merchant-assets/humans-s768ng/card_background.png",
        cardFontColor = "#FFFFFF",
        donationRecipient = DonationRecipient(name = "Sawyer Dog Fund", url = "https://getcatch.com"),
        defaultSignUpBonus = 1000.0,
        defaultSignUpDiscount = 0.0,
        unrestrictedRewardRuleId = "rr-f31675602e426246df73e2a5f8dac7da",
        newCatchUserRewardRuleId = "rr-ba191143213271854ac145ceeabf314e",
        theme = null,
    )
}
