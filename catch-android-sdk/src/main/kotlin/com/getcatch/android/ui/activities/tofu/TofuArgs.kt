package com.getcatch.android.ui.activities.tofu

import android.os.Parcelable
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.tofu.TofuPath
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class TofuArgs(
    val price: Int,
    val rewardsSummary: EarnedRewardsSummary,
    val path: TofuPath = TofuPath.HOW_IT_WORKS,
): Parcelable
