package com.getcatch.android.models

import com.getcatch.android.serialization.SnakeCaseSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.math.min

@Serializable
internal data class CalculatedAvailableRewardsBreakdown(
    val redeemableRewardsTotal: Int,
    val restrictedRewards: List<AvailableRewardDetail>,
    val restrictedRewardsTotal: Int
) {
    fun toJsonElement(): JsonElement = buildJsonObject {
        put("redeemableRewardsTotal", Json.encodeToJsonElement(redeemableRewardsTotal))
        put("restrictedRewards", SnakeCaseSerializer.encodeToJsonElement(restrictedRewards))
        put("restrictedRewardsTotal", Json.encodeToJsonElement(restrictedRewardsTotal))
    }

    companion object {
        fun calculate(
            price: Int,
            rawBreakdown: List<AvailableRewardDetail>
        ): CalculatedAvailableRewardsBreakdown {
            // For rewards restricted by percentage order total max,
            // limit the available rewards to the max percentage of the order total.
            val availableRewards = rawBreakdown.map { reward ->
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

            return CalculatedAvailableRewardsBreakdown(
                redeemableRewardsTotal = redeemableRewardsTotal,
                restrictedRewards = restrictedRewards,
                restrictedRewardsTotal = restrictedRewardsTotal
            )
        }
    }
}
