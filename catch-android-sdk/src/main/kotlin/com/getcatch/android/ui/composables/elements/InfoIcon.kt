package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.tofu.TofuPath
import com.getcatch.android.ui.activities.tofu.TOFUActivity
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.typography.CatchTextStyles

@Composable
internal fun InfoIcon(
    textStyle: TextStyle,
    price: Int? = null,
    rewardsSummary: EarnedRewardsSummary? = null,
) {
    val context = LocalContext.current
    Text(
        text = stringResource(R.string.info_character),
        style = textStyle,
        modifier = Modifier
            .padding(2.dp)
            .clickable(
                onClick = {
                    TOFUActivity.open(context, price, rewardsSummary, path = TofuPath.HOW_IT_WORKS)
                },
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
    )
}

@Preview
@Composable
private fun PreviewInfoIcon() {
    CatchTheme {
        InfoIcon(CatchTextStyles.bodySmall)
    }
}
