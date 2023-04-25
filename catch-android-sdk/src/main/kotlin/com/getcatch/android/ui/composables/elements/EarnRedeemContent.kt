package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.getcatch.android.models.CalculatedReward
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.utils.Constants
import com.getcatch.android.viewmodels.EarnRedeemUiState

@Composable
internal fun EarnRedeemContent(
    uiState: EarnRedeemUiState,
    styles: InfoWidgetStyle.Resolved,
    content: @Composable (reward: CalculatedReward, summary: EarnedRewardsSummary?) -> Unit
) {
    when (uiState) {
        EarnRedeemUiState.Loading -> {
            LoadingBar(
                modifier = Modifier
                    .width(Constants.LOADING_BAR_WIDTH.dp)
                    .height(styles.widgetTextStyle.lineHeight.value.dp)
            )
        }
        is EarnRedeemUiState.Success -> {
            content(uiState.reward, uiState.summary)
        }
    }
}
