package com.getcatch.android.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.viewmodels.RewardsByOrderIdUiState
import com.getcatch.android.viewmodels.RewardsByOrderIdViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun PurchaseConfirmationByOrderId(
    orderId: String,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    theme: ThemeVariant? = null,
    styleOverrides: ActionWidgetStyle? = null,
) {
    val viewModel: RewardsByOrderIdViewModel = koinViewModel(key = orderId)
    LaunchedEffect(key1 = orderId) {
        viewModel.loadRewards(orderId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    (uiState as? RewardsByOrderIdUiState.Success)?.let { state ->
        PurchaseConfirmation(
            earned = state.rewards.earnedRewards,
            donation = state.rewards.userDonationAmountFromReward,
            borderStyle = borderStyle,
            theme = theme,
            styleOverrides = styleOverrides
        )
    }
}
