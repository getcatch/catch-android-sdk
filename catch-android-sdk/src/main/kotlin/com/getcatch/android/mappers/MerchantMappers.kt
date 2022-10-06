package com.getcatch.android.mappers

import com.getcatch.android.clients.merchant.LoadPublicMerchantDataResponse
import com.getcatch.android.domain.Merchant

internal fun LoadPublicMerchantDataResponse.toMerchant(publicKey: String) = Merchant(
    id = id,
    publicKey = publicKey,
    name = name,
    rewardsRatePercent = rewardsRatePercent,
    url = url,
    cardBackground = cardBackground,
    rewardsLifetimeInDays = rewardsLifetimeInDays,
    cardFontColor = cardFontColor,
    hasTheme = theme != null,
)
