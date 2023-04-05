package com.getcatch.android.ui.composables.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.getcatch.android.models.CalculatedReward
import com.getcatch.android.ui.styles.InfoWidgetStyle

@Composable
internal fun BenefitText(
    modifier: Modifier = Modifier,
    reward: CalculatedReward,
    styles: InfoWidgetStyle.Resolved,
    capitalize: Boolean = true,
) {
    val message = reward.getMessage(capitalize)
    val color =
        if (reward.redeemable) styles.benefitTextStyle.redeemFontColor
        else styles.benefitTextStyle.earnFontColor

    Text(
        text = styles.applyTextTransform(message),
        color = color,
        style = styles.benefitComposeTextStyle,
        textDecoration = TextDecoration.Underline,
        modifier = modifier,
    )
}
