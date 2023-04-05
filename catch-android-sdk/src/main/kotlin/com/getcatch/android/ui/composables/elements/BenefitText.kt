package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.viewmodels.EarnRedeemUiState

@Composable
internal fun BenefitText(
    uiState: EarnRedeemUiState,
    styles: InfoWidgetStyle.Resolved,
    capitalize: Boolean = true,
    prefixComposable: @Composable (() -> Unit)? = null,
    suffixComposable: @Composable (() -> Unit)? = null,
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
                if (reward.redeemable) styles.benefitTextStyle.redeemFontColor
                else styles.benefitTextStyle.earnFontColor

            prefixComposable?.invoke()
            Text(
                text = styles.applyTextTransform(message),
                color = color,
                style = styles.benefitComposeTextStyle,
                textDecoration = TextDecoration.Underline
            )
            suffixComposable?.invoke()
        }
    }
}

@Composable
internal fun BenefitText(
    uiState: EarnRedeemUiState,
    styles: InfoWidgetStyle.Resolved,
    capitalize: Boolean = true,
    prefix: String? = null,
    suffix: String? = null,
) = BenefitText(
    uiState = uiState,
    styles = styles,
    capitalize = capitalize,
    prefixComposable = prefix?.let {
        @Composable {
            Text(
                text = styles.applyTextTransform(it),
                style = styles.composeTextStyle,
            )
            Spacer(modifier = Modifier.width(3.dp))
        }
    },
    suffixComposable = suffix?.let {
        @Composable {
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = styles.applyTextTransform(it),
                style = styles.composeTextStyle,
            )
        }
    },
)
