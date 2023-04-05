package com.getcatch.android.composables.elements

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.viewmodels.EarnRedeemUiState

@Composable
internal fun BenefitText(
    uiState: EarnRedeemUiState,
    capitalize: Boolean = true,
    prefixComposable: @Composable (() -> Unit)? = null,
    suffixComposable: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = CatchTypography.CatchTextStyles.bodySmall,
) {
    when (uiState) {
        is EarnRedeemUiState.Loading -> {
            LoadingBar(
                modifier = Modifier
                    .width(115.dp)
                    .height(12.dp)
            )
        }
        is EarnRedeemUiState.Success -> {
            val reward = uiState.reward
            val message = reward.getMessage(capitalize)
            val color =
                if (reward.redeemable) CatchTheme.colors.secondaryAccent
                else CatchTheme.colors.accent

            prefixComposable?.invoke()
            Text(
                text = message,
                color = color,
                style = textStyle,
                fontWeight = FontWeight.W700,
                textDecoration = TextDecoration.Underline
            )
            suffixComposable?.invoke()
        }
    }
}

@Composable
internal fun BenefitText(
    uiState: EarnRedeemUiState,
    capitalize: Boolean = true,
    prefix: String? = null,
    suffix: String? = null,
    textStyle: TextStyle = CatchTypography.CatchTextStyles.bodySmall,
) = BenefitText(
    uiState = uiState,
    capitalize = capitalize,
    prefixComposable = prefix?.let {
        @Composable {
            Text(
                text = it,
                style = textStyle,
                color = CatchTheme.colors.foreground,
            )
            Spacer(modifier = Modifier.width(3.dp))
        }
    },
    suffixComposable = suffix?.let {
        @Composable {
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = it,
                style = textStyle,
                color = CatchTheme.colors.foreground,
            )
        }
    },
    textStyle = textStyle,
)
