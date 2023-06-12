package com.getcatch.android.models.tofu

import com.getcatch.android.models.CalculatedAvailableRewardsBreakdown
import com.getcatch.android.models.DonationRecipient
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.serialization.SnakeCaseSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

internal data class TofuOpenData(
    val earnedRewardsBreakdown: EarnedRewardsSummary,
    val price: Int,
    val merchantDefaults: MerchantDefaults,
    val donationRecipient: DonationRecipient?,
    val publicUserData: PublicUserData,
    val path: TofuPath
) {

    private val isUserRedeeming = (publicUserData.rewardAmount ?: 0) > 0
    fun toJsonObject(): JsonObject {
        val availableRewardsBreakdown = CalculatedAvailableRewardsBreakdown.calculate(
            price,
            publicUserData.availableRewardBreakdown ?: emptyList()
        )
        return buildJsonObject {
            put(
                "earnedRewardsBreakdown",
                SnakeCaseSerializer.encodeToJsonElement(earnedRewardsBreakdown)
            )
            put("price", Json.encodeToJsonElement(price))
            put("merchantDefaults", Json.encodeToJsonElement(merchantDefaults))
            put("donationRecipient", SnakeCaseSerializer.encodeToJsonElement(donationRecipient))
            put("availableRewardsBreakdown", availableRewardsBreakdown.toJsonElement())
            put("publicUserData", SnakeCaseSerializer.encodeToJsonElement(publicUserData))
            put("userIsRedeeming", Json.encodeToJsonElement(isUserRedeeming))
            put("path", Json.encodeToJsonElement(path.path))
        }
    }
}
