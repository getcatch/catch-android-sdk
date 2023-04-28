package com.getcatch.android.models.tofu

import com.getcatch.android.models.Merchant
import kotlinx.serialization.Serializable

@Serializable
internal data class MerchantDefaults(
    val defaultEarnedRewardsRate: Double,
    val defaultSignUpBonus: Double,
    val defaultSignUpDiscount: Double,
    val enableConfigurableRewards: Boolean
) {
    companion object {
        fun fromMerchant(merchant: Merchant) = MerchantDefaults(
            defaultEarnedRewardsRate = merchant.defaultEarnedRewardsRate,
            defaultSignUpBonus = merchant.defaultSignUpBonus,
            defaultSignUpDiscount = merchant.defaultSignUpDiscount,
            enableConfigurableRewards = merchant.enableConfigurableRewards,
        )
    }
}
