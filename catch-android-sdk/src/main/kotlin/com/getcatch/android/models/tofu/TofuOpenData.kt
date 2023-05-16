package com.getcatch.android.models.tofu

import com.getcatch.android.models.DonationRecipient
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.serialization.SnakeCaseSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.math.min

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
        return buildJsonObject {
            put(
                "earnedRewardsBreakdown",
                SnakeCaseSerializer.encodeToJsonElement(earnedRewardsBreakdown)
            )
            put("price", Json.encodeToJsonElement(price))
            put("merchantDefaults", Json.encodeToJsonElement(merchantDefaults))
            put("donationRecipient", SnakeCaseSerializer.encodeToJsonElement(donationRecipient))
            put("availableRewardsBreakdown", Json.encodeToJsonElement(generateAvailableRewardsBreakdown()))
            put("publicUserData", SnakeCaseSerializer.encodeToJsonElement(publicUserData))
            put("userIsRedeeming", Json.encodeToJsonElement(isUserRedeeming))
            put("path", Json.encodeToJsonElement(path.path))
        }
    }

    private fun generateAvailableRewardsBreakdown(): TofuAvailableRewardsBreakdown {
        // For rewards restricted by percentage order total max,
        // limit the available rewards to the max percentage of the order total.
        val availableRewards = publicUserData.availableRewardBreakdown.map { reward ->
            return@map reward.redeemablePercentageOrderTotalMax?.let { percentageMax ->
                reward.copy(
                    amount = min(
                        reward.amount.toDouble(),
                        price.toDouble() * percentageMax
                    ).toInt()
                )
            } ?: reward
        }

        // Calculate the redeemable total by filtering out rewards where the order
        // total minimum has not been set and taking the sum of all other available rewards.
        val redeemableRewardsTotal = availableRewards.sumOf {
            val isRedeemable = price >= (it.redeemableFlatOrderTotalMin ?: 0)
            return@sumOf if (isRedeemable) it.amount else 0
        }

        // Filter out rewards restricted by order minimum
        val restrictedRewards = availableRewards.filter {
            price < (it.redeemableFlatOrderTotalMin ?: 0)
        }

        // Sum the amounts in all the available rewards with restrictions
        val restrictedRewardsTotal = restrictedRewards.sumOf { it.amount }

        return TofuAvailableRewardsBreakdown(
            redeemableRewardsTotal = redeemableRewardsTotal,
            restrictedRewards = restrictedRewards,
            restrictedRewardsTotal = restrictedRewardsTotal
        )
    }
}
