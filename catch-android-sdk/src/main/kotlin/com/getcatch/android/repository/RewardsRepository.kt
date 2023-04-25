package com.getcatch.android.repository

import com.getcatch.android.models.CalculateRewardsResult
import com.getcatch.android.models.Item
import com.getcatch.android.models.PublicUserData

internal interface RewardsRepository {
    suspend fun fetchCalculatedEarnedReward(
        user: PublicUserData,
        price: Int,
        items: List<Item>?,
        userCohorts: List<String>?
    ): CalculateRewardsResult
}
