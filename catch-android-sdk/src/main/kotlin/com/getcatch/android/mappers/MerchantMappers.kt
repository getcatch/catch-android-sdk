package com.getcatch.android.mappers

import com.getcatch.android.clients.merchants.LoadPublicMerchantDataResponse
import com.getcatch.android.domain.Merchant

internal fun LoadPublicMerchantDataResponse.toMerchant() = Merchant(
    id = id,
    name = name,
    rewardsRatePercent = rewardsRatePercent,
    url = url,
    cardBackground = cardBackground,
    rewardsLifetimeInDays = rewardsLifetimeInDays,
    cardFontColor = cardFontColor,
    hasTheme = theme != null,
)
