package com.getcatch.android.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.viewmodels.RewardsByOrderIdUiState
import com.getcatch.android.viewmodels.RewardsByOrderIdViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * The `PurchaseConfirmationByOrderId` widget serves the same purpose as the
 * [`PurchaseConfirmation`](PurchaseConfirmation) widget but is to be used
 * for purchases made with Catch's virtual card integration.
 *
 * @param orderId The order ID used to create the virtual card checkout for which the
 * purchase confirmation will be displayed.
 *
 * @param borderStyle The [`BorderStyle`](BorderStyle) that the widget renders.
 * Defaults to the [`BorderStyle.SlightRound`](BorderStyle.SlightRound) style.
 *
 * @param colorTheme The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set
 * globally on the [`Catch`](Catch) object will be used, which defaults to
 * [`CatchColorTheme.Light`](CatchColorTheme.Light).
 *
 * @param styleOverrides Style overrides which can be used to override the widget's default
 * appearance (ex. font size, color, weight, etc.).
 */
@Composable
public fun PurchaseConfirmationByOrderId(
    orderId: String,
    borderStyle: BorderStyle = BorderStyle.SlightRound,
    colorTheme: CatchColorTheme? = null,
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
            colorTheme = colorTheme,
            styleOverrides = styleOverrides
        )
    }
}
