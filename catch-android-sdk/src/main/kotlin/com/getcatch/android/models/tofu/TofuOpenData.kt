package com.getcatch.android.models.tofu

import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.serialization.SnakeCaseSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

internal data class TofuOpenData(
    val earnedRewardsBreakdown: EarnedRewardsSummary,
    val price: Int,
    val merchantDefaults: MerchantDefaults,
    val path: TofuPath
) {
    fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("earnedRewardsBreakdown", SnakeCaseSerializer.encodeToJsonElement(earnedRewardsBreakdown))
            put("price", Json.encodeToJsonElement(price))
            put("merchantDefaults", Json.encodeToJsonElement(merchantDefaults))
            put("path", Json.encodeToJsonElement(path.path))
        }
    }
}
