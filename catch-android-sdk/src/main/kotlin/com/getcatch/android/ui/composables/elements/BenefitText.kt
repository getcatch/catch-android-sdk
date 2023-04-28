package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import com.getcatch.android.models.CalculatedReward
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.tofu.TofuPath
import com.getcatch.android.ui.activities.tofu.TOFUActivity
import com.getcatch.android.ui.styles.InfoWidgetStyle

@Composable
internal fun BenefitText(
    modifier: Modifier = Modifier,
    price: Int,
    reward: CalculatedReward,
    summary: EarnedRewardsSummary?,
    styles: InfoWidgetStyle.Resolved,
    capitalize: Boolean = true,
) {
    val context = LocalContext.current
    val message = reward.getMessage(capitalize)
    val color =
        if (reward.redeemable) styles.benefitTextStyle.redeemFontColor
        else styles.benefitTextStyle.earnFontColor
    val textDecoration =
        if (reward is CalculatedReward.PercentRate) null
        else TextDecoration.Underline

    Text(
        text = styles.applyTextTransform(message),
        color = color,
        style = styles.benefitComposeTextStyle,
        textDecoration = textDecoration,
        modifier = modifier.clickable(
            enabled = textDecoration == TextDecoration.Underline,
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            }) {
            TOFUActivity.open(context, price, summary, path = TofuPath.BREAKDOWN)
        },
    )
}
